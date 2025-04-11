package com.OrderManagement.Order.domain;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Order {
    private Long id;
    private final List<Product> products;
    private final LocalDateTime orderDate;
    private final Long clientId;
    private PaymentDto payment;
    private final StatusOrder statusOrder;
    private final double totalPrice;

    public Order(List<Product> products, Long clientId, PaymentDto payment, StatusOrder statusOrder) {
        validateProducts(products);
        validateClientId(clientId);
        validatePayment(payment);
        validateStatusOrder(statusOrder);

        this.products = products;
        this.orderDate = LocalDateTime.now();
        this.clientId = clientId;
        this.payment = payment;
        this.statusOrder = statusOrder;
        this.totalPrice = calculateTotalPrice();
    }

    public Order(Long id, List<Product> products, LocalDateTime orderDate, Long clientId, StatusOrder statusOrder) {
        validateProducts(products);
        validateClientId(clientId);
        validateStatusOrder(statusOrder);

        this.id = id;
        this.products = products;
        this.orderDate = orderDate;
        this.clientId = clientId;
        this.statusOrder = statusOrder;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        validateTotalPrice(totalPrice);
        return totalPrice;
    }

    private static void validateProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be null or empty");
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
    private static void validateStatusOrder(StatusOrder statusOrder) {
        if (statusOrder == null) {
            throw new IllegalArgumentException("Status order cannot be null");
        }
    }

    private static void validateTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price must be a positive number");
        }
    }
}
