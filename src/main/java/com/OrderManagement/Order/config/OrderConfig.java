package com.OrderManagement.Order.config;

import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;
import com.OrderManagement.Order.gateway.database.jpa.repository.ProductVOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    OrderJpaGateway orderJpaGateway(OrderRepository orderRepository, ProductVOrderRepository productRepository) {
        return new OrderJpaGateway(orderRepository, productRepository);
    }
}
