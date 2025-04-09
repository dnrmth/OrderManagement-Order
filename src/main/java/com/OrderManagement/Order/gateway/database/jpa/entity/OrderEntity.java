package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.enums.StatusOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private StatusOrder status;

}
