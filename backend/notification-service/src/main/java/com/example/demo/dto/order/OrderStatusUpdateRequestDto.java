package com.example.demo.dto.order;

import jakarta.validation.constraints.NotBlank;

public record OrderStatusUpdateRequestDto(@NotBlank String status) {
}