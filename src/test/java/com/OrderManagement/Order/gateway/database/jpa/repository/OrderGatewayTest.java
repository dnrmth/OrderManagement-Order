package com.OrderManagement.Order.gateway.database.jpa.repository;

import com.OrderManagement.Order.config.OrderConfig;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.database.jpa.OrderJpaGateway;
import com.OrderManagement.Order.gateway.database.jpa.entity.OrderEntity;
import com.OrderManagement.Order.gateway.database.jpa.entity.ProductVOrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(OrderConfig.class)
public class OrderGatewayTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductVOrderRepository productVOrderRepository;

    private OrderJpaGateway orderJpaGateway;

    private Order createdOrder;

    @BeforeEach
    void setUp() {
        orderJpaGateway = new OrderJpaGateway(orderRepository, productVOrderRepository);
        List<ProductVOrder> productVOrders = List.of(new ProductVOrder(1L, "SKU123", 2, 100.0));
        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);

        Order order = new Order(
                productVOrders,
                1L,
                payment,
                StatusOrder.ABERTO
        );
        createdOrder =  orderJpaGateway.createOrder(order);
    }

    @Test
    void testCreateOrder() {
        List<ProductVOrder> productVOrders = List.of(new ProductVOrder(1L, "SKU123", 2, 100.0));
        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);

        Order order = new Order(
                productVOrders,
                1L,
                payment,
                StatusOrder.ABERTO
        );

        Order createdOrder = orderJpaGateway.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(order.getClientId(), createdOrder.getClientId());
        assertEquals(order.getProducts().size(), createdOrder.getProducts().size());
        assertEquals(order.getTotalPrice(), createdOrder.getTotalPrice());

    }

    @Test
    void testFindOrderById() {
        long orderId = createdOrder.getId();
        Order foundOrder = orderJpaGateway.findOrderById(orderId);

        assertNotNull(foundOrder);
        assertEquals(createdOrder.getId(), foundOrder.getId());
        assertEquals(createdOrder.getStatusOrder(), foundOrder.getStatusOrder());
        assertEquals(createdOrder.getProducts().size(), foundOrder.getProducts().size());
    }

    @Test
    void testFindOrdersByClientId() {
        long clientId = createdOrder.getClientId();
        List<Order> orders = orderJpaGateway.findOrdersByClientId(clientId);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(clientId, orders.getFirst().getClientId());
    }

    @Test
    void testCancelOrder() {
        long orderId = createdOrder.getId();
        orderJpaGateway.cancelOrder(orderId);

        OrderEntity canceledOrder = orderRepository.findById(orderId).orElseThrow();
        assertEquals(StatusOrder.CANCELED, canceledOrder.getStatus());
    }
}
