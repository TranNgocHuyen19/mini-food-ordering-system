package com.example.demo.dto.order;

import com.example.demo.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequestDto(@NotNull OrderStatus status) {
}