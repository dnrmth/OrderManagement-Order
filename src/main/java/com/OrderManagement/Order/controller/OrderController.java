package com.OrderManagement.Order.controller;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.domain.enums.StatusOrder;

import com.OrderManagement.Order.usecase.cancel_order.ICancelOrderUseCase;
import com.OrderManagement.Order.usecase.create_order.ICreateOrderUseCase;
import com.OrderManagement.Order.usecase.get_order.IGetOrderUseCase;
import com.OrderManagement.Order.usecase.get_order.IGetOrdersByClientIdUseCase;
import com.OrderManagement.Order.usecase.update_order.IUpdateOrderUseCase;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final ICreateOrderUseCase createOrderUseCase;
    private final IGetOrderUseCase getOrderUseCase;
    private final IGetOrdersByClientIdUseCase getOrdersByClientId;
    private final IUpdateOrderUseCase updateOrderUseCase;
    private final ICancelOrderUseCase cancelOrderUseCase;

    public OrderController(ICreateOrderUseCase createOrderUseCase,
                           IGetOrderUseCase getOrderUseCase,
                           IGetOrdersByClientIdUseCase getOrdersByClientId,
                           IUpdateOrderUseCase updateOrderUseCase,
                           ICancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getOrdersByClientId = getOrdersByClientId;
        this.updateOrderUseCase = updateOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @PostMapping
    @Transactional
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
            return createOrderUseCase.createOrder(orderDto);
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
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
        return cancelOrderUseCase.cancelOrder(orderId);
    }
}
