package com.iuh.fit.food_service.service;

import com.iuh.fit.food_service.model.Food;
import com.iuh.fit.food_service.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    @Override
    @CircuitBreaker(name = "default", fallbackMethod = "foodListFallback")
    @Retry(name = "default", fallbackMethod = "foodListFallback")
    @RateLimiter(name = "default", fallbackMethod = "foodListFallback")
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }
    
    @Override
    @CircuitBreaker(name = "default", fallbackMethod = "foodListFallback")
    @Retry(name = "default", fallbackMethod = "foodListFallback")
    @RateLimiter(name = "default", fallbackMethod = "foodListFallback")
    public List<Food> getFoodsByCategory(String category) {
        return foodRepository.findByCategory(category);
    }
    
    @Override
    @CircuitBreaker(name = "default", fallbackMethod = "optionalFoodFallback")
    @Retry(name = "default", fallbackMethod = "optionalFoodFallback")
    @RateLimiter(name = "default", fallbackMethod = "optionalFoodFallback")
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }
    
    @Override
    @CircuitBreaker(name = "default", fallbackMethod = "foodFallback")
    @Retry(name = "default", fallbackMethod = "foodFallback")
    @RateLimiter(name = "default", fallbackMethod = "foodFallback")
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }
    
    @Override
    public Food updateFood(Long id, Food food) {
        return foodRepository.findById(id)
            .map(existingFood -> {
                if (food.getName() != null) {
                    existingFood.setName(food.getName());
                }
                if (food.getPrice() != null) {
                    existingFood.setPrice(food.getPrice());
                }
                if (food.getDescription() != null) {
                    existingFood.setDescription(food.getDescription());
                }
                if (food.getImageUrl() != null) {
                    existingFood.setImageUrl(food.getImageUrl());
                }
                if (food.getCategory() != null) {
                    existingFood.setCategory(food.getCategory());
                }
                if (food.getQuantity() != null) {
                    existingFood.setQuantity(food.getQuantity());
                }
                return foodRepository.save(existingFood);
            })
            .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
    }
    
    @Override
    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new RuntimeException("Food not found with id: " + id);
        }
        foodRepository.deleteById(id);
    }
    
    @Override
    public Food updateQuantity(Long id, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        return foodRepository.findById(id)
            .map(existingFood -> {
                existingFood.setQuantity(quantity);
                return foodRepository.save(existingFood);
            })
            .orElseThrow(() -> new RuntimeException("Food not found with id: " + id));
    }
    
    @Override
    public boolean isInStock(Long id, Integer requiredQuantity) {
        return foodRepository.findById(id)
            .map(food -> food.getQuantity() >= requiredQuantity)
            .orElse(false);
    }

    public List<Food> foodListFallback(Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Food Service is currently unavailable. Reason: " + t.getMessage());
    }

    public List<Food> foodListFallback(String category, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Food Service is currently unavailable. Reason: " + t.getMessage());
    }

    public Optional<Food> optionalFoodFallback(Long id, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Food Service is currently unavailable. Reason: " + t.getMessage());
    }

    public Food foodFallback(Food food, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Food Service is currently unavailable. Reason: " + t.getMessage());
    }
}
