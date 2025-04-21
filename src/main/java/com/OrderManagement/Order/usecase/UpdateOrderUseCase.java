package com.OrderManagement.Order.usecase;

import com.OrderManagement.Order.domain.Order;
import com.OrderManagement.Order.enums.StatusOrder;

public class UpdateOrderUseCase {

    public static Order updateOrderStatus(Order order, StatusOrder statusOrder) {
        if(order.getStatusOrder().equals(statusOrder)) {
            throw new IllegalArgumentException("Status already set to " + statusOrder);
        }
        order.setStatusOrder(statusOrder);

        return order;
    }
}
