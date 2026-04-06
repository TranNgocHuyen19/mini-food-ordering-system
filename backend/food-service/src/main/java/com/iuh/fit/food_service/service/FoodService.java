package com.iuh.fit.food_service.service;

import com.iuh.fit.food_service.model.Food;
import java.util.List;
import java.util.Optional;

public interface FoodService {
    List<Food> getAllFoods();
    Optional<Food> getFoodById(Long id);
    Food createFood(Food food);
    Food updateFood(Long id, Food food);
    void deleteFood(Long id);
    Food updateQuantity(Long id, Integer quantity);
    boolean isInStock(Long id, Integer requiredQuantity);
}
