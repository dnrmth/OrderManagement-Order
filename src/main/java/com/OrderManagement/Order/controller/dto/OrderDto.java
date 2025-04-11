package com.OrderManagement.Order.controller.dto;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.Product;
import com.OrderManagement.Order.enums.StatusOrder;

import java.util.List;

public record OrderDto(
    List<ProductDto> products,
    Long clientId,
    PaymentDto payment,
    StatusOrder statusOrder
){
    public OrderDto(Order order) {

        this(order.getProducts().stream()
                .map(product -> new ProductDto(product.getProductId(), product.getQuantity(), product.getPrice()))
                .toList(),
                order.getClientId(),
                order.getPayment(),
                order.getStatusOrder());
    }

}

