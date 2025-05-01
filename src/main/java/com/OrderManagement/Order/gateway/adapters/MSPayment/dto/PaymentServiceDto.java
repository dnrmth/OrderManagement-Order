package com.OrderManagement.Order.gateway.adapters.MSPayment.dto;

public record PaymentServiceDto(Long id, CardServiceDTO card, Double orderValue, Long orderId) {
}
