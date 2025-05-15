package com.OrderManagement.Order.gateway.adapters.MSClient.fallback;

import com.OrderManagement.Order.gateway.adapters.MSClient.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceFallback implements ClientService {

    @Override
    public ResponseEntity<Boolean> confirmClientIsActive(Long clientId) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(false);
    }
}
