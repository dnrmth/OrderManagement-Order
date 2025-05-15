package com.OrderManagement.Order.gateway.adapters.MSPayment;

import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSPayment.fallback.PaymentServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PaymentService",
        url = "${payment.service.url}",
        fallback = PaymentServiceFallback.class)
public interface PaymentService {

    @PostMapping("/makePayment")
    ResponseEntity<PaymentServiceDto> makePayment(PaymentServiceDto payment);
}
