package com.OrderManagement.Order.gateway.database.jpa;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.database.jpa.entity.OrderEntity;
import com.OrderManagement.Order.gateway.database.jpa.entity.ProductVOrderEntity;
import com.OrderManagement.Order.gateway.database.jpa.repository.OrderRepository;
import com.OrderManagement.Order.gateway.database.jpa.repository.ProductVOrderRepository;

import java.util.List;

public class OrderJpaGateway implements IOderGateway {

    private final OrderRepository orderRepository;
    private final ProductVOrderRepository productVOrderRepository;

    public OrderJpaGateway(OrderRepository orderRepository, ProductVOrderRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productVOrderRepository = productRepository;
    }

    @Override
    public Order createOrder(Order order) {

        OrderEntity orderEntity = orderRepository.save(new OrderEntity(order));
        List<ProductVOrderEntity> productEntity = productVOrderRepository.saveAll(convertToProductEntityList(order.getProducts(), orderEntity.getId()));

        return orderEntity.toDomain(productEntity);
    }

    @Override
    public Order findOrderById(long id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        List<ProductVOrderEntity> productEntity = productVOrderRepository.findAllByOrderId(id);

        return orderEntity.toDomain(productEntity);
    }

    @Override
    public List<Order> findOrdersByClientId(long clientId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByClientId(clientId);
        if (orderEntities.isEmpty()) {
            throw new RuntimeException("No orders found for this customer");
        }

        return convertToOrderList(orderEntities);
    }

    public void cancelOrder(Long orderId) {
        var orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        orderEntity.setStatus(StatusOrder.CANCELED);
        orderRepository.save(orderEntity);
    }

    private List<ProductVOrderEntity> convertToProductEntityList(List<ProductVOrder> products, Long orderId) {
        return products.stream()
                .map(product ->
                        new ProductVOrderEntity(orderId,
                                product.getProductId(),
                                product.getQuantity(),
                                product.getPrice())).toList();
    }

    private List<Order> convertToOrderList(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
                .map(orderEntity -> {
                    List<ProductVOrderEntity> productEntity = productVOrderRepository.findAllByOrderId(orderEntity.getId());
                    return orderEntity.toDomain(productEntity);
                }).toList();
    }
}
