package com.OrderManagement.Order.usecase.update_order;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderUseCase implements IUpdateOrderUseCase{

    private final IOderGateway orderGateway;

    public OrderDto updateOrderStatus(Long orderId, StatusOrder statusOrder) {
        if (statusOrder == null) {
            throw new IllegalArgumentException("Status order cannot be null");
        }
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        try {
            Order order = orderGateway.findOrderById(orderId);
            order.setStatusOrder(statusOrder);

            return new OrderDto(orderGateway.createOrder(order));
        }catch (Exception e) {
            throw new IllegalArgumentException("Error updating order status: " + e.getMessage());
        }
    }
}
