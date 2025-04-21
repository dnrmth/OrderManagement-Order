package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.enums.StatusOrder;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.usecase.CreateOrderUseCase;
import com.OrderManagement.Order.usecase.UpdateOrderUseCase;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return new OrderDto(orderJpaGateway.createOrder(order));
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        var order = orderJpaGateway.findOrderById(orderId);

        return new OrderDto(order);
    }

    @GetMapping("/client/{clientId}")
    public List<OrderDto> getOrdersByClientId(@PathVariable Long clientId) {
        var orders = orderJpaGateway.findOrdersByClientId(clientId);

        return orders.stream()
                .map(OrderDto::new)
                .toList();
    }

    @PutMapping("/update/{orderId}/{statusOrder}")
    public OrderDto updateOrderStatus(@PathVariable Long orderId, @PathVariable StatusOrder statusOrder) {
        var order = orderJpaGateway.findOrderById(orderId);
        var updatedOrder = UpdateOrderUseCase.updateOrderStatus(order, statusOrder);

        return new OrderDto(orderJpaGateway.createOrder(updatedOrder));
    }
}
