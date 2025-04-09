package com.OrderManagement.Order.gateway.database.jpa.entity;

import jakarta.persistence.*;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Long productId;

    private int quantity;

    private double price;

    public ProductEntity() {
    }
}
