package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.usecase.CreateOrderUseCase;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderJpaGateway orderJpaGateway;

    public OrderController(OrderJpaGateway orderJpaGateway) {
        this.orderJpaGateway = orderJpaGateway;
}

    @PostMapping
    @Transactional
    public OrderDto createOrder(@RequestBody OrderDto createOrderDto) {

        var order = CreateOrderUseCase.createOrder(createOrderDto.products(),
                createOrderDto.clientId(),
                createOrderDto.payment(),
                createOrderDto.statusOrder());

        orderJpaGateway.createOrder(order);

        return new OrderDto(order);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        var order = orderJpaGateway.findOrderById(orderId);

        return new OrderDto(order);
    }
}
