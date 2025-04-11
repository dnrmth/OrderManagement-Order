package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.controller.dto.ProductDto;
import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.controller.dto.PaymentDto;

import com.OrderManagement.Order.domain.Product;
import com.OrderManagement.Order.enums.StatusOrder;

import java.util.List;

public class CreateOrderUseCase {

    public static Order createOrder(List<ProductDto> products, Long clientId, PaymentDto payment, StatusOrder statusOrder) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty");
        }
        List<Product> productList = products.stream()
                .map(productDto -> new Product(productDto.productId(), productDto.quantity(), productDto.price()))
                .toList();

        return new Order(productList, clientId, payment, statusOrder);
    }

}
