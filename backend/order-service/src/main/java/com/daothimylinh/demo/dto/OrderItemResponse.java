package com.daothimylinh.demo.dto;

public record OrderItemResponse(
        Long foodId,
        String foodName,
        int quantity,
        double price
) {
}
