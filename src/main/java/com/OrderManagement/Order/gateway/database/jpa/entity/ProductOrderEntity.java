package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Table(name = "order_v_product")
public class ProductOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "order_id")
    private Long orderId;

    @Setter
    @Column(name = "product_id")
    private Long productId;

    @Setter
    @Column(name = "quantity")
    private int quantity;

    @Setter
    @Column(name = "price")
    private double currentPrice;

    public ProductOrderEntity() {
    }
    public ProductOrderEntity(Long orderId, Long productId, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.currentPrice = price;
    }

    public ProductOrderEntity(Product product) {
        this.productId = product.getProductId();
        this.quantity = product.getQuantity();
        this.currentPrice = product.getPrice();
    }

    public Product toDomain() {
        return new Product(
                this.productId,
                this.quantity,
                this.currentPrice
        );
    }
}
