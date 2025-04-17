package com.OrderManagement.Order.gateway.database.jpa.repository;

import com.OrderManagement.Order.gateway.database.jpa.entity.ProductVOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVOrderRepository extends JpaRepository<ProductVOrderEntity, Long> {

    @Query(value = """
            SELECT * FROM product_v_order p
            WHERE p.order_id = :orderId
            """, nativeQuery = true)
    List<ProductVOrderEntity> findAllByOrderId(Long orderId);
}
