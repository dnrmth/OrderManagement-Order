package com.OrderManagement.Order.gateway.adapters.MSPayment.fallback;

import com.OrderManagement.Order.gateway.adapters.MSPayment.PaymentService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public ResponseEntity<PaymentServiceDto> makePayment(PaymentServiceDto paymentServiceDto) {

        PaymentServiceDto failedPayment = new PaymentServiceDto(null,
                paymentServiceDto.card(),
                paymentServiceDto.orderValue(),
                paymentServiceDto.orderId());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(failedPayment);
    }
}
