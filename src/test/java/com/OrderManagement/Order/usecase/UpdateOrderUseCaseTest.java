package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.usecase.update_order.UpdateOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UpdateOrderUseCaseTest {
    @Mock
    private IOderGateway orderGateway;

    @InjectMocks
    private UpdateOrderUseCase updateOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateOrderStatusSuccessfully() {
        Long orderId = 1L;
        ProductVOrder product1 = new ProductVOrder(1L, "SKU123", 2, 100.0);
        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);
        StatusOrder statusOrder = StatusOrder.ABERTO;
        Order order = new Order(List.of(product1), orderId,payment, StatusOrder.CANCELED);
        order.setStatusOrder(statusOrder);

        when(orderGateway.findOrderById(orderId)).thenReturn(order);
        when(orderGateway.createOrder(order)).thenReturn(order);

        OrderDto result = updateOrderUseCase.updateOrderStatus(orderId, statusOrder);

        assertNotNull(result);
        assertEquals(statusOrder, result.statusOrder());
        verify(orderGateway, times(1)).findOrderById(orderId);
        verify(orderGateway, times(1)).createOrder(order);
    }

    @Test
    void shouldThrowExceptionWhenStatusOrderIsNull() {
        Long orderId = 1L;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                updateOrderUseCase.updateOrderStatus(orderId, null));

        assertEquals("Status order cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderIdIsNull() {
        StatusOrder statusOrder = StatusOrder.FECHADO_COM_SUCESSO;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                updateOrderUseCase.updateOrderStatus(null, statusOrder));

        assertEquals("Order ID cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOrderNotFound() {
        Long orderId = 1L;
        StatusOrder statusOrder = StatusOrder.FECHADO_COM_SUCESSO;

        when(orderGateway.findOrderById(orderId)).thenThrow(new RuntimeException("Order not found"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                updateOrderUseCase.updateOrderStatus(orderId, statusOrder));

        assertTrue(exception.getMessage().contains("Error updating order status: Order not found"));
        verify(orderGateway, times(1)).findOrderById(orderId);
    }

}
