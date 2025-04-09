package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.domain.dto.ProductDto;
import com.OrderManagement.Order.enums.StatusOrder;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    private StatusOrder status;

    private Long clientId;



}
