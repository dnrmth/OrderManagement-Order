package com.OrderManagement.Order.Config;

import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;
import com.OrderManagement.Order.gateway.database.jpa.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    OrderJpaGateway orderJpaGateway(OrderRepository orderRepository, ProductRepository productRepository) {
        return new OrderJpaGateway(orderRepository, productRepository);
    }
}
