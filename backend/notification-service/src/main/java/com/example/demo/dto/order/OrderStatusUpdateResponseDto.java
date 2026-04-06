package com.example.demo.dto.order;

import com.example.demo.model.OrderStatus;

public record OrderStatusUpdateResponseDto(Long orderId, OrderStatus status) {
}