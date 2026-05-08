package com.example.demo.service;

import com.example.demo.client.OrderServiceClient;
import com.example.demo.dto.payment.PaymentRequestDto;
import com.example.demo.dto.payment.PaymentResponseDto;
import com.example.demo.model.Payment;
import com.example.demo.model.OrderStatus;
import com.example.demo.model.PaymentStatus;
import com.example.demo.repository.PaymentRepository;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class PaymentService {
	private static final Logger logger = Logger.getLogger(PaymentService.class.getName());

	private final PaymentRepository paymentRepository;
	private final OrderServiceClient orderServiceClient;
	private final NotificationService notificationService;

	public PaymentService(
			PaymentRepository paymentRepository,
			OrderServiceClient orderServiceClient,
			NotificationService notificationService) {
		this.paymentRepository = paymentRepository;
		this.orderServiceClient = orderServiceClient;
		this.notificationService = notificationService;
	}

		@CircuitBreaker(name = "default", fallbackMethod = "createPaymentFallback")
	@Retry(name = "default", fallbackMethod = "createPaymentFallback")
	@RateLimiter(name = "default", fallbackMethod = "createPaymentFallback")
	public PaymentResponseDto createPayment(PaymentRequestDto request) {
		Payment payment = new Payment();
		payment.setOrderId(request.orderId());
		payment.setUserName(request.userName());
		payment.setAmount(request.amount());
		payment.setStatus(PaymentStatus.PENDING);
		payment = paymentRepository.save(payment);

		orderServiceClient.updateOrderStatus(request.orderId(), OrderStatus.COMPLETED);

		String notificationMessage = notificationService.sendPaymentSuccessNotification(request);
		payment.setStatus(PaymentStatus.COMPLETED);
		payment = paymentRepository.save(payment);
		logger.info(notificationMessage);
		return toResponse(payment);
	}

	public PaymentResponseDto createPaymentFallback(PaymentRequestDto request, Throwable t) {
		throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Dependent services are currently down, please try again later. Reason: " + t.getMessage());
	}

	private PaymentResponseDto toResponse(Payment payment) {
		return new PaymentResponseDto(
				payment.getId(),
				payment.getOrderId(),
				payment.getUserName(),
				payment.getAmount(),
				payment.getStatus(),
				payment.getCreatedAt(),
				payment.getUpdatedAt());
	}
}