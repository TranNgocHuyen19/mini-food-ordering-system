package com.example.demo.service;

import com.example.demo.dto.payment.PaymentRequestDto;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

	public String sendPaymentSuccessNotification(PaymentRequestDto request) {
		String message = String.format("%s đã đặt đơn #%d thành công", request.userName(), request.orderId());
		logger.info(message);
		return message;
	}
}