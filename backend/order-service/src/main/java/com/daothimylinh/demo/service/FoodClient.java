package com.daothimylinh.demo.service;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "foodClient", url = "${app.food-service.url}")
public interface FoodClient {

	@GetMapping("/api/foods/{foodId}")
	Map<String, Object> getFoodByIdRaw(@PathVariable("foodId") Long foodId);

	@SuppressWarnings("unchecked")
	default Map<String, Object> getFoodById(Long foodId) {
		Map<String, Object> response = getFoodByIdRaw(foodId);
		if (response == null) {
			return null;
		}

		Object data = response.get("data");
		if (data instanceof Map<?, ?> dataMap) {
			return (Map<String, Object>) dataMap;
		}

		if (response.get("id") != null) {
			return response;
		}
		return null;
	}
}
