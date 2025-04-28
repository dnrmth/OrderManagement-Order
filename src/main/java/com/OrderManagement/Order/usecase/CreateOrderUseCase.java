package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.ProductVOrderDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.controller.dto.PaymentDto;

import com.OrderManagement.Order.domain.ProductVOrder;
import com.OrderManagement.Order.domain.enums.StatusOrder;
import com.OrderManagement.Order.gateway.IOderGateway;
import com.OrderManagement.Order.gateway.adapters.MSClient.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final IOderGateway orderGateway;
    private final ClientService clientService;

    public OrderDto createOrder(List<ProductVOrderDto> products, Long clientId, PaymentDto payment, StatusOrder statusOrder) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty");
        }
        if(!clientService.confirmClientIsActive(clientId)){
            throw new IllegalArgumentException("Client is not active");
        }
        List<ProductVOrder> productList = products.stream()
                .map(productDto -> new ProductVOrder(productDto.productSKU(), productDto.quantity(), productDto.price()))
                .toList();

        var orderEntity = orderGateway.createOrder(new Order(productList, clientId, payment, statusOrder));

        return new OrderDto(orderEntity);
    }
}
