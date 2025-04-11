package com.OrderManagement.Order.gateway.database.jpa;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.Product;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.database.jpa.entity.OrderEntity;
import com.OrderManagement.Order.gateway.database.jpa.entity.ProductOrderEntity;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;
import com.OrderManagement.Order.gateway.database.jpa.repository.ProductRepository;

import java.util.List;


public class OrderJpaGateway implements IOderGateway {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderJpaGateway(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order createOrder(Order order) {

        OrderEntity orderEntity = orderRepository.save(new OrderEntity(order));
        List<ProductOrderEntity> productEntity = productRepository.saveAll(convertToProductEntityList(order.getProducts(), orderEntity.getId()));

        return orderEntity.toDomain(productEntity);
    }

    private List<ProductOrderEntity> convertToProductEntityList(List<Product> products, Long orderId) {
        return products.stream()
                .map(product ->
                        new ProductOrderEntity(orderId,
                                product.getProductId(),
                                product.getQuantity(),
                                product.getPrice())).toList();
    }
}
