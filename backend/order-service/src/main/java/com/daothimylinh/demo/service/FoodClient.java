package com.daothimylinh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class FoodClient {
    private final RestTemplate restTemplate;
    private final String foodServiceUrl;

    @Autowired
    public FoodClient(RestTemplate restTemplate, @Value("${app.food-service.url}") String foodServiceUrl) {
        this.restTemplate = restTemplate;
        this.foodServiceUrl = foodServiceUrl;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getFoodById(Long foodId) {
        try {
            // Food service contract: GET /api/foods/{id} -> { success, message, data, timestamp }
            Map<String, Object> response = restTemplate.getForObject(
                    foodServiceUrl + "/api/foods/" + foodId,
                    Map.class
            );

            if (response == null) {
                return null;
            }

            Object data = response.get("data");
            if (data instanceof Map<?, ?> dataMap) {
                return (Map<String, Object>) dataMap;
            }

            // Fallback for non-wrapped payloads
            if (response.get("id") != null) {
                return response;
            }
            return null;
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
