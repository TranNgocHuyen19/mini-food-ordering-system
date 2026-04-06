package com.daothimylinh.demo.dto;

import com.daothimylinh.demo.model.OrderStatus;

public record UpdateOrderStatusRequest(
        OrderStatus status
) {
}
