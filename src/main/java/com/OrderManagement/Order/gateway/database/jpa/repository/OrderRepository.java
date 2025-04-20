package com.OrderManagement.Order.gateway.database.jpa.repository;

import com.OrderManagement.Order.gateway.database.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(countQuery = """
            SELECT * FROM orders o
            WHERE o.client_id = :customerId
            """, nativeQuery = true)
    List<OrderEntity> findAllByClientId(long customerId);

}
