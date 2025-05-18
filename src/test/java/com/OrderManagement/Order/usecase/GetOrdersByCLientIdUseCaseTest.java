package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.usecase.get_order.GetOrdersByClientIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class GetOrdersByCLientIdUseCaseTest {

    @Mock
    private IOderGateway orderGateway;

    @InjectMocks
    private GetOrdersByClientIdUseCase getOrdersByClientIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnOrdersWhenClientIdIsValid() {

        Long clientId = 1L;
        ProductVOrder product1 = new ProductVOrder(1L, "SKU123", 2, 100.0);
        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);
        var mockOrders = List.of(new Order(List.of(product1), clientId, payment, StatusOrder.ABERTO), new Order(List.of(product1), clientId, payment, StatusOrder.ABERTO));
        when(orderGateway.findOrdersByClientId(clientId)).thenReturn(mockOrders);

        var result = getOrdersByClientIdUseCase.getOrdersByClientId(clientId);

        assertEquals(mockOrders.size(), result.size());
        verify(orderGateway, times(1)).findOrdersByClientId(clientId);
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersFound() {

        Long clientId = 2L;
        when(orderGateway.findOrdersByClientId(clientId)).thenReturn(List.of());

        var result = getOrdersByClientIdUseCase.getOrdersByClientId(clientId);

        assertEquals(0, result.size());
        verify(orderGateway, times(1)).findOrdersByClientId(clientId);
    }
}
