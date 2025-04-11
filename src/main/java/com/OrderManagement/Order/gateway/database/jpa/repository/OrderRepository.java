package com.OrderManagement.Order.gateway.database.jpa.repository;

import com.OrderManagement.Order.gateway.database.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}
