package com.example.demo.service;

import com.example.demo.client.OrderServiceClient;
import com.example.demo.dto.order.OrderStatusUpdateRequestDto;
import com.example.demo.dto.payment.PaymentRequestDto;
import com.example.demo.dto.payment.PaymentResponseDto;
import com.example.demo.model.Payment;
import com.example.demo.model.PaymentStatus;
import com.example.demo.repository.PaymentRepository;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	private static final String ORDER_STATUS_PAID = "PAID";
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

	public PaymentResponseDto createPayment(PaymentRequestDto request) {
		Payment payment = new Payment();
		payment.setOrderId(request.orderId());
		payment.setUserName(request.userName());
		payment.setAmount(request.amount());
		payment.setStatus(PaymentStatus.PENDING);
		payment = paymentRepository.save(payment);

		try {
			orderServiceClient.updateOrderStatus(request.orderId(), new OrderStatusUpdateRequestDto(ORDER_STATUS_PAID));
		} catch (Exception exception) {
			logger.warning(() -> String.format(
					"Failed to update order status for order #%d: %s",
					request.orderId(),
					exception.getMessage()));
		}

		String notificationMessage = notificationService.sendPaymentSuccessNotification(request);
		payment.setStatus(PaymentStatus.COMPLETED);
		payment = paymentRepository.save(payment);
		logger.info(notificationMessage);
		return toResponse(payment);
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