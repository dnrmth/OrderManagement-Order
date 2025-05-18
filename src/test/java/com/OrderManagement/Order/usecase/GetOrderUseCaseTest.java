package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.usecase.get_order.GetOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetOrderUseCaseTest {

    @Mock
    private IOderGateway orderGateway;

    @InjectMocks
    private GetOrderUseCase getOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrder_ShouldReturnOrderDto_WhenOrderExists() {
        Long orderId = 1L;
        List<ProductVOrder> productVOrder = List.of(new ProductVOrder(1L, 2, 123));
        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);
        Order order = new Order(productVOrder, orderId, payment, StatusOrder.ABERTO);
        OrderDto expectedOrderDto = new OrderDto(order);

        when(orderGateway.findOrderById(orderId)).thenReturn(order);

        OrderDto result = getOrderUseCase.getOrder(orderId);

        assertEquals(expectedOrderDto, result);
        verify(orderGateway, times(1)).findOrderById(orderId);
    }
}
