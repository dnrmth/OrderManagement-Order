package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.usecase.CreateOrderUseCase;
import com.OrderManagement.Order.usecase.UpdateOrderUseCase;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOderGateway orderGateway;

    public OrderController(OrderJpaGateway orderJpaGateway) {
        this.orderGateway = orderJpaGateway;
}

    @PostMapping
    @Transactional
    public OrderDto createOrder(@RequestBody OrderDto createOrderDto) {
        var order = CreateOrderUseCase.createOrder(createOrderDto.products(),
                createOrderDto.clientId(),
                createOrderDto.payment(),
                createOrderDto.statusOrder());

        return new OrderDto(orderGateway.createOrder(order));
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        var order = orderGateway.findOrderById(orderId);

        return new OrderDto(order);
    }

    @GetMapping("/client/{clientId}")
    public List<OrderDto> getOrdersByClientId(@PathVariable Long clientId) {
        var orders = orderGateway.findOrdersByClientId(clientId);

        return orders.stream()
                .map(OrderDto::new)
                .toList();
    }

    @PutMapping("/update/{orderId}/{statusOrder}")
    @Transactional
    public OrderDto updateOrderStatus(@PathVariable Long orderId, @PathVariable StatusOrder statusOrder) {
        var order = orderGateway.findOrderById(orderId);
        var updatedOrder = UpdateOrderUseCase.updateOrderStatus(order, statusOrder);

        return new OrderDto(orderGateway.createOrder(updatedOrder));
    }

    @DeleteMapping("/cancel/{orderId}")
    @Transactional
    public ResponseEntity cancelOrder(@PathVariable Long orderId){
        var order = orderGateway.findOrderById(orderId);

        orderGateway.cancelOrder(order);

        return ResponseEntity.noContent().build();
    }
}
