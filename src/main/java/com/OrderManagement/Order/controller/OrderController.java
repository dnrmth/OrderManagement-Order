package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.usecase.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final GetOrdersByClientId getOrdersByClientId;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping
    @Transactional
    public OrderDto createOrder(@RequestBody OrderDto createOrderDto) {

        return createOrderUseCase.createOrder(createOrderDto.products(),
                createOrderDto.clientId(),
                createOrderDto.payment(),
                createOrderDto.statusOrder());
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable Long orderId) {
        return getOrderUseCase.getOrder(orderId);
    }

    @GetMapping("/client/{clientId}")
    public List<OrderDto> getOrdersByClientId(@PathVariable Long clientId) {
        return getOrdersByClientId.getOrdersByClientId(clientId);
    }

    @PutMapping("/update/{orderId}/{statusOrder}")
    @Transactional
    public OrderDto updateOrderStatus(@PathVariable Long orderId, @PathVariable StatusOrder statusOrder) {
        return updateOrderUseCase.updateOrderStatus(orderId, statusOrder);
    }

    @DeleteMapping("/cancel/{orderId}")
    @Transactional
    public ResponseEntity cancelOrder(@PathVariable Long orderId){
        return cancelOrderUseCase.cancelOrder(orderId);
    }
}
