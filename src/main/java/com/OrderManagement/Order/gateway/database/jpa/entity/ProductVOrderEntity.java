package com.OrderManagement.Order.gateway.database.jpa.entity;

import com.OrderManagement.Order.domain.ProductVOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "product_v_order")
public class ProductVOrderEntity {

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

    public ProductVOrderEntity() {
    }
    public ProductVOrderEntity(Long orderId, Long productId, int quantity, double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.currentPrice = price;
    }

    public ProductVOrderEntity(ProductVOrder product) {
        this.productId = product.getProductSKU();
        this.quantity = product.getQuantity();
        this.currentPrice = product.getPrice();
    }

    public ProductVOrder toDomain() {
        return new ProductVOrder(
                this.productId,
                this.quantity,
                this.currentPrice
        );
    }
}
