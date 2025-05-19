package com.OrderManagement.Order.gateway.adapters.MSPayment;

import com.OrderManagement.Order.gateway.adapters.MSClient.fallback.ClientServiceFallback;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.CardServiceDTO;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSPayment.fallback.PaymentServiceFallback;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentServiceFallbackTest {

    @Test
    void MakePayment(){
        PaymentServiceFallback paymentServiceFallback = new PaymentServiceFallback();

        PaymentServiceDto paymentServiceDto = new PaymentServiceDto(
                1L,
                new CardServiceDTO("1234567890123456", 123, "John", "12/25"),
                134.0,
                1L
        );

        ResponseEntity<PaymentServiceDto> response = paymentServiceFallback.makePayment(paymentServiceDto);

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(paymentServiceDto.card(), response.getBody().card());
        assertEquals(paymentServiceDto.orderValue(), response.getBody().orderValue());
        assertEquals(paymentServiceDto.orderId(), response.getBody().orderId());
    }
}

