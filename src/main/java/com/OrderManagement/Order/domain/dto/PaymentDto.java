package com.OrderManagement.Order.domain.dto;

public record PaymentDto(Long paymentId, int cardNumber, String cardType, String cardHolderName, String cardExpiryDate, int cvv) {

}
