package com.OrderManagement.Order.gateway.adapters.MSClient;

import com.OrderManagement.Order.gateway.adapters.MSClient.fallback.ClientServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ClientService",
        url = "${client.service.url}",
        fallback = ClientServiceFallback.class)
public interface ClientService {

    @GetMapping(value = "/isActive/{clientId}")
    ResponseEntity<Boolean> confirmClientIsActive(@PathVariable Long clientId);
}