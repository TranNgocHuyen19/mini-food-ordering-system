package com.example.demo.client;

import com.example.demo.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userServiceClient", url = "${app.user-service.url}")
public interface UserServiceClient {

	@GetMapping("/api/users/validate")
	ApiResponse<Boolean> validateToken();
}