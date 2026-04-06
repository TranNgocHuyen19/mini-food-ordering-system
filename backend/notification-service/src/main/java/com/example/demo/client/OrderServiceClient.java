package com.example.demo.client;

import com.example.demo.model.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderServiceClient", url = "${app.order-service.url}")
public interface OrderServiceClient {

	@PutMapping("/orders/{id}/status")
	void updateOrderStatus(@PathVariable("id") Long id, @RequestBody OrderStatus status);
}