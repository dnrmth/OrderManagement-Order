package com.OrderManagement.Order.gateway.adapters.MSClient;

import com.OrderManagement.Order.gateway.adapters.MSPayment.fallback.PaymentServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ClientService",
        url = "http://localhost:8082/client/api")
public interface ClientService {

    @GetMapping(value = "/isActive/{clientId}")
    boolean confirmClientIsActive(@PathVariable Long clientId);
}
