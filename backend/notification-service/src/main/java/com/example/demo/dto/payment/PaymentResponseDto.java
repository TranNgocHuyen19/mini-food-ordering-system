package com.example.demo.dto.payment;

import com.example.demo.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponseDto(
		Long id,
		Long orderId,
		String userName,
		BigDecimal amount,
		PaymentStatus paymentStatus,
		Instant createdAt,
		Instant updatedAt) {
}