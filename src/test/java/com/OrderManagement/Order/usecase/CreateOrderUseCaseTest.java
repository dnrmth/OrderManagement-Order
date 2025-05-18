package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.adapters.MSClient.ClientService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.PaymentService;
import com.OrderManagement.Order.gateway.adapters.MSPayment.dto.PaymentServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSProduct.ProductService;
import com.OrderManagement.Order.gateway.adapters.MSProduct.dto.ProductServiceDto;
import com.OrderManagement.Order.gateway.adapters.MSStock.StockService;
import com.OrderManagement.Order.gateway.adapters.MSStock.fallback.StockDto;
import com.OrderManagement.Order.usecase.create_order.CreateOrderUseCase;
import com.sun.istack.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CreateOrderUseCaseTest {
    @Mock
    private IOderGateway orderGateway;

    @Mock
    private ClientService clientService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private ProductService productService;

    @Mock
    private StockService stockService;

    @Mock
    private KafkaTemplate<String, OrderDto> kafkaTemplate;

    @InjectMocks
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Long clientId = 1L;

    ProductVOrder product1 = new ProductVOrder(1L, "SKU123", 2, 100.0);
    ProductServiceDto productServiceDto = new ProductServiceDto(2L, "test", "teste product", "250518-0001", 100.0, 2 );

    PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);

    Order order = new Order(List.of(product1), 1L, payment, StatusOrder.ABERTO);

    OrderDto orderDto = new OrderDto(order);

    @Test
    void createOrder_ShouldCreateOrderSuccessfully() {
        Long clientId = 1L;

        ProductVOrder product1 = new ProductVOrder(1L, "SKU123", 2, 100.0);
        ProductServiceDto productServiceDto = new ProductServiceDto(2L, "test", "teste product", "250518-0001", 100.0, 2 );

        PaymentDto payment = new PaymentDto("1234567890123456", "123", "John Doe", "12/25", 134);

        Order order = new Order(List.of(product1), 1L, payment, StatusOrder.ABERTO);

        OrderDto orderDto = new OrderDto(order);

        when(clientService.confirmClientIsActive(clientId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(productService.getProductBySKU(product1.getProductId())).thenReturn(new ResponseEntity<>(productServiceDto, HttpStatus.OK));
        when(stockService.removeQuantityInventory(product1.getSKU(), product1.getQuantity())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(orderGateway.createOrder(any(Order.class))).thenReturn(new Order(List.of(product1), 1L, payment, StatusOrder.ABERTO));
        when(paymentService.makePayment(any(PaymentServiceDto.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(kafkaTemplate.send(anyString(), any(OrderDto.class))).thenReturn(null);

        OrderDto createdOrder = createOrderUseCase.createOrder(orderDto);

        assertNotNull(createdOrder);
        verify(clientService, times(1)).confirmClientIsActive(clientId);
        verify(productService, times(1)).getProductBySKU(productServiceDto.id());
        verify(stockService, times(1)).removeQuantityInventory(productServiceDto.sku(), productServiceDto.quantity());
        verify(orderGateway, times(1)).createOrder(any(Order.class));
        verify(paymentService, times(1)).makePayment(any(PaymentServiceDto.class));
        verify(kafkaTemplate, times(1)).send(anyString(), any(OrderDto.class));
    }

    @Test
    void createOrder_ShouldThrowException_WhenClientIsNotActive() {

        OrderDto orderDto = new OrderDto(order);

        when(clientService.confirmClientIsActive(clientId)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createOrderUseCase.createOrder(orderDto));
        assertEquals("Client is not active", exception.getMessage());
        verify(clientService, times(1)).confirmClientIsActive(clientId);
        verifyNoInteractions(productService, stockService, orderGateway, paymentService, kafkaTemplate);
    }

    @Test
    void createOrder_ShouldThrowException_WhenProductListIsEmpty() {

        OrderDto orderDto = new OrderDto( List.of(),1L, null, StatusOrder.ABERTO);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createOrderUseCase.createOrder(orderDto));
        assertEquals("Product list cannot be null or empty", exception.getMessage());
        verifyNoInteractions(clientService, productService, stockService, orderGateway, paymentService, kafkaTemplate);
    }

    @Test
    void createOrder_ShouldThrowException_WhenStockUpdateFails() {

        Long clientId = 1L;
        ProductVOrder productDto = new ProductVOrder(1L, 123, 100.0);
        OrderDto orderDto = new OrderDto(order);

        when(clientService.confirmClientIsActive(clientId)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(productService.getProductBySKU(productDto.getProductId())).thenReturn(new ResponseEntity<>(productServiceDto, HttpStatus.OK));
        when(stockService.removeQuantityInventory(productDto.getSKU(), productDto.getQuantity())).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createOrderUseCase.createOrder(orderDto));
        assertTrue(exception.getMessage().contains("Stock update failed"));
        verify(clientService, times(1)).confirmClientIsActive(clientId);
        verify(productService, times(1)).getProductBySKU(productDto.getProductId());
        verify(stockService, times(1)).removeQuantityInventory(productDto.getSKU(), productDto.getQuantity());
        verifyNoInteractions(orderGateway, paymentService, kafkaTemplate);
    }
}
