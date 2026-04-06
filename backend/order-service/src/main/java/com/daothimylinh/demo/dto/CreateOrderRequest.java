package com.daothimylinh.demo.dto;

import java.util.List;

public record CreateOrderRequest(
        Long userId,
        List<OrderItemRequest> items
) {
}
