package com.OrderManagement.Order.usecase.cancel_order;

import org.springframework.http.ResponseEntity;

public interface ICancelOrderUseCase {
    ResponseEntity<?> cancelOrder(Long orderId);
}
