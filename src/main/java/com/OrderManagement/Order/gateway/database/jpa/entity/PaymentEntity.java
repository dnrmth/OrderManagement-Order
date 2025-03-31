package com.OrderManagement.Order.gateway.database.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PaymentEntity {
    @Id
    private Long id;
    private int cardNumber;
}
