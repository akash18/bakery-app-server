package com.yummy.bakery.service.impl;

import com.yummy.bakery.entity.*;
import com.yummy.bakery.repository.OrderRepository;
import com.yummy.bakery.service.OrderService;
import com.yummy.bakery.service.ProductService;
import com.yummy.bakery.utilities.Constants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by alalwani on 03/07/17.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    EntityManager em;

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        HttpURLConnection connection = null;
        try {
            StringBuilder result = new StringBuilder();
            connection = getHttpConnection(Constants.IONIC_USERS_API,
                    RequestMethod.GET.toString(),
                    Constants.IONIC_API_AUTH);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                Map<String, User> usersMap = parseResult(result.toString());
                orders = orderRepository.findAll(sortByOrderTimeDesc());
                updateUserDetails(usersMap, orders);
            } else {
                System.err.println("Ionic server returned error!! Response Code: "+ Integer.toString(connection.getResponseCode()));
            }
        } catch (Exception e){
            System.out.print("Error while fetching order/user information.");
        } finally {
            connection.disconnect();
        }
        return orders;
    }

    private Sort sortByOrderTimeDesc() {
        return new Sort(Sort.Direction.DESC, "created");
    }

    private Map<String,User> parseResult(String result){
        Map<String,User> usersMap = new HashMap<>();
        JSONObject object = new JSONObject(result);
        JSONArray dataArray = object.getJSONArray("data");

        for(int i=0; i<dataArray.length(); i++){
            User user = new User();
            UserDetails userDetails = new UserDetails();
            JSONObject userObject = (JSONObject) dataArray.get(i);
            user.setUserId(userObject.getString("uuid"));
            JSONObject details = (JSONObject) userObject.get("details");
            JSONObject custom = (JSONObject) userObject.get("custom");

            user.setEmail(details.getString("email"));
            user.setUserName(details.getString("username"));
            if(details.isNull("name")){
                userDetails.setFullName(null);
            }else{
                userDetails.setFullName(details.getString("name"));
            }

            // Custom User Details
            userDetails.setPhoneNumber(custom.getLong("phoneNumber"));
            userDetails.setAddr1(custom.getString("addr1"));
            userDetails.setAddr2(custom.getString("addr2"));
            userDetails.setCity(custom.getString("city"));
            // Temporary check until database data is corrupted
           /* try{
                if(custom.getString("postalCode") == ""){
                    userDetails.setPostalCode(0);
                }
            }catch (Exception e){
                userDetails.setPostalCode(custom.getInt("postalCode"));
            }*/
            userDetails.setPostalCode(String.valueOf(custom.get("postalCode")));
            user.setUserDetails(userDetails);

            usersMap.put(user.getUserId(),user);
        }

        return usersMap;
    }

    private void updateUserDetails(Map<String, User> usersMap, List<Order> orders){
        for(Order order: orders){
            order.setUser(usersMap.get(order.getUserId()));
        }
    }

    private static HttpURLConnection getHttpConnection(String apiUrl, String requestMethod, String authorization) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)(new URL(apiUrl)).openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod(requestMethod);
        conn.setRequestProperty("Authorization", authorization);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    @Override
    public void updateOrder(Order order) {
        Order dbOrder = orderRepository.findOne(order.getId());
        dbOrder.setStatus(order.getStatus());
        orderRepository.saveAndFlush(dbOrder);
    }

    @Override
    public Order saveOrder(Order order) {
        order = orderRepository.saveAndFlush(order);
        Set<OrderDetails> details = new HashSet<>(order.getOrderDetails());
        for(OrderDetails detail : details) {
            em.merge(detail.getProduct());
        }
        return order;
    }

    @Override
    public boolean checkOrder(Order order) {
        Set<OrderDetails> details = new HashSet<>(order.getOrderDetails());
        for(OrderDetails detail : details) {
            long id = detail.getProduct().getId();
            Product product = productService.getProductById(id);
            if(product.getProductQuantity() < detail.getQuantityRequired()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Order> getOrders(String userId) {
        TypedQuery<Order> q2 =
                em.createQuery("Select a from Order a where a.userId=:userId", Order.class);
        q2.setParameter("userId", userId);
        List<Order> orders = q2.getResultList();
        return orders;
    }
}
