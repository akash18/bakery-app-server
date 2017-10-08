package com.yummy.bakery.service;

import com.yummy.bakery.entity.Order;

import java.util.List;

/**
 * Created by alalwani on 03/07/17.
 */
public interface OrderService {

    List<Order> findAll();

    void updateOrder(Order order);

    Order saveOrder(Order order);

    boolean checkOrder(Order order);

    List<Order> getOrders(String userId);
}
