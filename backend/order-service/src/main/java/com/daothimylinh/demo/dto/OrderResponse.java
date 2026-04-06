package com.daothimylinh.demo.dto;

import com.daothimylinh.demo.model.OrderStatus;

import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        List<OrderItemResponse> items,
        OrderStatus status
) {
}
