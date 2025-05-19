package com.OrderManagement.Order.gateway.adapters.MSClient;

import com.OrderManagement.Order.gateway.adapters.MSClient.fallback.ClientServiceFallback;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceFallbackTest {
    @Test
    void confirmClientIsActive(){
        ClientServiceFallback clientServiceFallback = new ClientServiceFallback();
        var response = clientServiceFallback.confirmClientIsActive(1L);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
    }
}
