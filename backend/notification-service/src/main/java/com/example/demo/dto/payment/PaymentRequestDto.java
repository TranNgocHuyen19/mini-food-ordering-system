package com.example.demo.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PaymentRequestDto(
		@NotNull Long orderId,
		@NotBlank String userName,
		@NotNull @Positive BigDecimal amount) {
}