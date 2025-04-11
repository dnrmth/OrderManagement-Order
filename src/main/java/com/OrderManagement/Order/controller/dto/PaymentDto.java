package com.OrderManagement.Order.controller.dto;

public record PaymentDto(
        int cardNumber,
        String cardType,
        String cardHolderName,
        String cardExpiryDate,
        int cvv){
}
