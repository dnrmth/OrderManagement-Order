package com.OrderManagement.Order.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderDto(
    List<ProductDto> products,
    LocalDateTime orderDate,
    Long clientId,
    PaymentDto payment
){}
