package com.daothimylinh.demo.dto;

public record OrderItemRequest(
        Long foodId,
        int quantity
) {
}
