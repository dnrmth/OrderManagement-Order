package com.OrderManagement.Order.usecase.update_order;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.domain.enums.StatusOrder;

public interface IUpdateOrderUseCase {
    OrderDto updateOrderStatus(Long orderId, StatusOrder statusOrder);
}
