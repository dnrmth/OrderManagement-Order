package com.OrderManagement.Order.usecase.get_order;

import com.OrderManagement.Order.controller.dto.OrderDto;

import java.util.List;

public interface IGetOrdersByClientIdUseCase {

    List<OrderDto> getOrdersByClientId(Long clientId);
}
