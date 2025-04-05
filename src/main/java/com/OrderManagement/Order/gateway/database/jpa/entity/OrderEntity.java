package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.domain.dto.ProductDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<ProductEntity> products;


    private String orderDate;


    private Long clientId;

}
