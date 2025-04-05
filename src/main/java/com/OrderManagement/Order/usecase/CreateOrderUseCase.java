package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.domain.dto.PaymentDto;
import com.OrderManagement.Order.domain.dto.ProductDto;

import java.time.LocalDateTime;
import java.util.List;

public class CreateOrderUseCase {

    public static Order createOrder(List<ProductDto> products, LocalDateTime orderDate, Long clientId, PaymentDto payment) {

        return new Order(products, orderDate, clientId, payment);
    }

}
