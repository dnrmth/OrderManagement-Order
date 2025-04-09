package com.OrderManagement.Order.gateway.database.jpa;

import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;

public class OrderJpaGateway implements IOderGateway {

    private final OrderRepository orderRepository;

    public OrderJpaGateway(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
