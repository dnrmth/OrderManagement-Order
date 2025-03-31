package com.OrderManagement.Order.gateway.database.jpa.repository;

import com.OrderManagement.Order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
