package com.OrderManagement.Order.usecase.get_order;

import com.OrderManagement.Order.controller.dto.OrderDto;

public interface IGetOrderUseCase {

    OrderDto getOrder(Long orderId);
}
