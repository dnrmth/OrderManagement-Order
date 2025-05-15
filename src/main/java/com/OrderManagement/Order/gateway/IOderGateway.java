package com.OrderManagement.Order.gateway;

import com.OrderManagement.Order.domain.Order;

import java.util.List;

public interface IOderGateway {
    Order createOrder(Order order);

    Order findOrderById(long id);

    List<Order> findOrdersByClientId(long customerId);

    void cancelOrder(Long order);
}
