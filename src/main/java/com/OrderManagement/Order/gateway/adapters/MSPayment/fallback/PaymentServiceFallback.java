package com.OrderManagement.Order.gateway.adapters.MSPayment.fallback;

import com.OrderManagement.Order.gateway.adapters.MSPayment.PaymentService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {

    @Override
    public ResponseEntity<PaymentServiceDto> makePayment(PaymentServiceDto paymentServiceDto) {

        return ResponseEntity.ok(new PaymentServiceDto(paymentServiceDto.id(),
                paymentServiceDto.card(),
                paymentServiceDto.orderValue(),
                9999L));
    }
}
