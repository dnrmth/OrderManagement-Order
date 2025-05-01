package com.OrderManagement.Order.gateway.adapters.MSPayment.dto;

public record CardServiceDTO(String number, Integer cvv, String nameOnCard, String expirationDate) {
}
