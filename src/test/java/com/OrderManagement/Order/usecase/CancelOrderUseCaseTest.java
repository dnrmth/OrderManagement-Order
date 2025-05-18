package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.usecase.cancel_order.CancelOrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CancelOrderUseCaseTest {
    @Mock
    private IOderGateway orderGateway;

    @InjectMocks
    private CancelOrderUseCase cancelOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cancelOrder_ShouldReturnNoContent_WhenOrderIsCancelledSuccessfully() {
        // Arrange
        Long orderId = 1L;
        doNothing().when(orderGateway).cancelOrder(orderId);

        // Act
        ResponseEntity<?> response = cancelOrderUseCase.cancelOrder(orderId);

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCode().value());
        verify(orderGateway, times(1)).cancelOrder(orderId);
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenOrderIdIsNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cancelOrderUseCase.cancelOrder(null));
        assertEquals("Order ID cannot be null", exception.getMessage());
        verifyNoInteractions(orderGateway);
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenGatewayThrowsException() {
        // Arrange
        Long orderId = 1L;
        doThrow(new RuntimeException("Gateway error")).when(orderGateway).cancelOrder(orderId);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> cancelOrderUseCase.cancelOrder(orderId));
        assertEquals("Error canceling order: Gateway error", exception.getMessage());
        verify(orderGateway, times(1)).cancelOrder(orderId);
    }
}
