package com.OrderManagement.Order.controller.dto;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.enums.StatusOrder;

import java.util.List;

public record OrderDto(
    List<ProductVOrderDto> products,
    Long clientId,
    PaymentDto payment,
    StatusOrder statusOrder
){
    public OrderDto(Order order) {

        this(order.getProducts().stream()
                .map(product -> new ProductVOrderDto(product.getProductId(), product.getQuantity(), product.getPrice()))
                .toList(),
                order.getClientId(),
                order.getPayment(),
                order.getStatusOrder());
    }
}

