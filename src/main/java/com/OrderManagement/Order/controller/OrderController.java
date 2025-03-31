package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.dto.CreateOrderDto;
import com.OrderManagement.Order.usecase.CreateOrderUseCase;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oder")
public class OrderController {

    private CreateOrderUseCase createOrderUseCase;

    public Order createOrder(@RequestBody CreateOrderDto createOrderDto) {

        return createOrderUseCase.createOrder()

        return new Order(
                createOrderDto.products(),
                createOrderDto.orderDate(),
                createOrderDto.clientId(),
                createOrderDto.payment()
        );
    }
}
