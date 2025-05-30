package com.OrderManagement.Order.usecase.get_order;

import com.OrderManagement.Order.controller.dto.OrderDto;
import com.OrderManagement.Order.gateway.IOderGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase implements IGetOrderUseCase {

    private final IOderGateway orderGateway;

    public OrderDto getOrder(Long orderId) {
        var order = orderGateway.findOrderById(orderId);
        return new OrderDto(order);
    }
}
