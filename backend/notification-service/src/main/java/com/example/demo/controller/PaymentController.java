package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.payment.PaymentRequestDto;
import com.example.demo.dto.payment.PaymentResponseDto;
import com.example.demo.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<PaymentResponseDto>> createPayment(@Valid @RequestBody PaymentRequestDto request) {
		PaymentResponseDto paymentResponse = paymentService.createPayment(request);
		ApiResponse<PaymentResponseDto> response = ApiResponse.success("Payment created successfully", paymentResponse);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}