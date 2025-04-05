package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.dto.CreateOrderDto;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.usecase.CreateOrderUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderJpaGateway orderJpaGateway;

    public OrderController(OrderJpaGateway orderJpaGateway) {
        this.orderJpaGateway = orderJpaGateway;
}

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderDto createOrderDto) {

        Order order = CreateOrderUseCase.createOrder(createOrderDto.products(), createOrderDto.orderDate(),
                createOrderDto.clientId(), createOrderDto.payment());

        return order;
    }
}
