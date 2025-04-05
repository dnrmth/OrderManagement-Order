package com.OrderManagement.Order.gateway.database.jpa;

import com.OrderManagement.Order.gateway.OderGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;

public class OrderJpaGateway implements OderGateway {
    private final OrderRepository orderRepository;

    public OrderJpaGateway(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

}
