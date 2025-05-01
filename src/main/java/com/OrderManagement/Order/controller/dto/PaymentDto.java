package com.OrderManagement.Order.controller.dto;

public record PaymentDto(
        String cardNumber,
        String cardType,
        String cardHolderName,
        String cardExpiryDate,
        int cvv){
}
