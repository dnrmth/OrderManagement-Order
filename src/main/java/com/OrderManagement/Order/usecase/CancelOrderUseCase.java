package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.gateway.IOderGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderUseCase {

    private final IOderGateway orderGateway;

    public CancelOrderUseCase(IOderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public ResponseEntity cancelOrder(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        try {
            orderGateway.cancelOrder(orderId);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Error canceling order: " + e.getMessage());
        }
    }
}
