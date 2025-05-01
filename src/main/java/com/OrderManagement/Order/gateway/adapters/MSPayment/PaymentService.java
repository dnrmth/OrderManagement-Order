package com.OrderManagement.Order.gateway.adapters.MSPayment;

import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PaymentService", url = "http://localhost:8083/payment")
public interface PaymentService {

    @PostMapping("/makePayment")
    ResponseEntity<PaymentServiceDto> makePayment(PaymentServiceDto payment);
}
