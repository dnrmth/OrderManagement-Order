package com.OrderManagement.Order.gateway.database.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductEntity {
    @Id
    private Long id;

}
