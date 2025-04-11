package com.OrderManagement.Order.gateway;


import com.OrderManagement.Order.domain.Order;

public interface IOderGateway {

    Order createOrder(Order order);
}
