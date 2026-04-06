package com.example.demo.client;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.order.OrderStatusUpdateRequestDto;
import com.example.demo.dto.order.OrderStatusUpdateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderServiceClient", url = "${app.order-service.url}")
public interface OrderServiceClient {

	@PutMapping("/orders/{orderId}/status")
	ApiResponse<OrderStatusUpdateResponseDto> updateOrderStatus(
			@PathVariable("orderId") Long orderId,
			@RequestBody OrderStatusUpdateRequestDto request);
}