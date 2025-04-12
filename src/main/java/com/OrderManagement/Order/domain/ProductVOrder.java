package com.OrderManagement.Order.domain;

import lombok.Getter;

@Getter
public class ProductVOrder {
    private Long id;

    private Long orderId;

    private Long productId;

    private int quantity;

    private double price;

    public ProductVOrder(Long productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
