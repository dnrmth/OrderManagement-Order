package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
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

    @Column(name = "total_price")
    private double totalPrice;

    public OrderEntity() {
    }

    public OrderEntity(Order order) {
        this.id = order.getId();
        this.clientId = order.getClientId();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatusOrder();
        this.totalPrice = order.getTotalPrice();
    }

    public Order toDomain(List<ProductVOrderEntity> productOrderEntity) {
        return new Order(
                this.id,
                productOrderEntity.stream().map(ProductVOrderEntity::toDomain).toList(),
                this.orderDate,
                this.clientId,
                this.status
        );
    }

}
