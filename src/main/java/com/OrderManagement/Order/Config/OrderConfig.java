package com.OrderManagement.Order.Config;

import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public OrderJpaGateway orderJpaGateway(OrderRepository orderRepository) {
        return new OrderJpaGateway(orderRepository);
    }
}
