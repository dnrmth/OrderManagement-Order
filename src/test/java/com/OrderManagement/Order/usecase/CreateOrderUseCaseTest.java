package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.enums.StatusOrder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class CreateOrderUseCaseTest {

    @Test
    void createOrder(){


        assertDoesNotThrow(() -> {
            CreateOrderUseCase.createOrder(List.of(),
                    1L,
                    new PaymentDto(
                      1234567890,  "0cardType",  "cardHolderName", "cardExpiryDate", 123
            ), StatusOrder.PENDING);
        });
    }
}
