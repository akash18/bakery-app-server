package com.yummy.bakery.controller;

import com.yummy.bakery.entity.Order;
import com.yummy.bakery.entity.OrderDetails;
import com.yummy.bakery.entity.Product;
import com.yummy.bakery.service.OrderService;
import com.yummy.bakery.service.ProductService;
import com.yummy.bakery.service.impl.AndroidPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;

	@CrossOrigin
	@RequestMapping(value = "/orders/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserOrders(@PathVariable("userId") String userId) {
		List<Order> orders = orderService.getOrders(userId);
		if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
	
	@CrossOrigin
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public ResponseEntity<?> saveOrder(@RequestBody Order order) {
    	if(orderService.checkOrder(order)) {
    		order = orderService.saveOrder(order);
    		for(OrderDetails details: order.getOrderDetails()){
				productService.updateProduct(details.getProduct());
			}

			AndroidPush push = new AndroidPush();
			try {
				push.sendPushNotification("Order", "New order has been placed by "+order.getUserId());
			}catch (Exception e){
				System.err.println("Error in sending push notification !!");
			}
    		return new ResponseEntity<>(order, HttpStatus.CREATED);
    	}
    	List<Product> products = productService.getProducts();
    	return new ResponseEntity<>(products, HttpStatus.BAD_REQUEST);
	}

	@CrossOrigin
	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getOrders(){
		List<Order> orders = new ArrayList<>();
		try {
			orders.addAll(orderService.findAll());
		}catch (Exception ex){
			return new ResponseEntity<>(orders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping("/order")
	public ResponseEntity<Order> updateOrder(@RequestBody Order order){
		try {
			orderService.updateOrder(order);
		}catch (Exception ex){
			return new ResponseEntity<>(order, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
