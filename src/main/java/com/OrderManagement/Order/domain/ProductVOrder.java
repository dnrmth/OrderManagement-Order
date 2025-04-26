package com.OrderManagement.Order.domain;

import lombok.Getter;

@Getter
public class ProductVOrder {
    private Long id;

    private Long orderId;

    private Long productSKU;

    private int quantity;

    private double price;

    public ProductVOrder(Long productId, int quantity, double price) {
        this.productSKU = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
