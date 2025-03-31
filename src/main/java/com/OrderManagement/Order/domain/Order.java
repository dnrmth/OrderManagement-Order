package com.OrderManagement.Order.domain;

import com.OrderManagement.Order.domain.dto.PaymentDto;
import com.OrderManagement.Order.domain.dto.ProductDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {
    private Long orderID;
    private List<ProductDto> products;
    private LocalDateTime orderDate;
    private Long clientId;
    private PaymentDto payment;

    public Order(List<ProductDto> products, LocalDateTime orderDate, Long clientId, PaymentDto payment) {
        validateProducts(products);
        validateOrderDate(orderDate);
        validateClientId(clientId);
        validatePayment(payment);

        this.products = products;
        this.orderDate = orderDate;
        this.clientId = clientId;
        this.payment = payment;
    }

    private static void validateProducts(List<ProductDto> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
        }
    }
    private static void validateOrderDate(LocalDateTime orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("Order date cannot be null");
        }
    }
    private static void validateClientId(Long clientId) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("Client ID must be a positive number");
        }
    }
    private static void validatePayment(PaymentDto payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
    }
}
