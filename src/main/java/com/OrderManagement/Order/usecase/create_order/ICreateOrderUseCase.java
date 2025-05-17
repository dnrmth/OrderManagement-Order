package com.OrderManagement.Order.usecase.create_order;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.controller.dto.PaymentDto;
import com.OrderManagement.Order.controller.dto.ProductVOrderDto;
import com.OrderManagement.Order.domain.enums.StatusOrder;

import java.util.List;

public interface ICreateOrderUseCase {

    OrderDto createOrder(OrderDto orderDto);

}
