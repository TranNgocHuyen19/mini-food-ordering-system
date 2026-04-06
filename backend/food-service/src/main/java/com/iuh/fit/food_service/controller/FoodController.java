package com.iuh.fit.food_service.controller;

import com.iuh.fit.food_service.dto.ApiResponse;
import com.iuh.fit.food_service.model.Food;
import com.iuh.fit.food_service.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class FoodController {
    
    private final FoodService foodService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Food>>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.ok(ApiResponse.success("Danh sách tất cả món ăn", foods));
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Food>>> getFoodsByCategory(@PathVariable String category) {
        List<Food> foods = foodService.getFoodsByCategory(category);
        if (foods.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.success("Danh sách món ăn theo danh mục", foods));
        }
        return ResponseEntity.ok(ApiResponse.success("Danh sách món ăn theo danh mục: " + category, foods));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Food>> getFoodById(@PathVariable Long id) {
        return foodService.getFoodById(id)
            .map(food -> ResponseEntity.ok(ApiResponse.success("Thông tin chi tiết món ăn", food)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("Không tìm thấy món ăn với id: " + id, null)));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Food>> createFood(@RequestBody Food food) {
        try {
            Food createdFood = foodService.createFood(food);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo món ăn mới thành công", createdFood));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.failure("Lỗi khi tạo món ăn: " + e.getMessage(), null));
        }
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Food>> updateFood(@PathVariable Long id, @RequestBody Food food) {
        try {
            Food updatedFood = foodService.updateFood(id, food);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật món ăn thành công", updatedFood));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("Không tìm thấy món ăn với id: " + id, null));
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa món ăn thành công", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("Không tìm thấy món ăn với id: " + id, null));
        }
    }
    
    @GetMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<InventoryResponse>> checkStock(@PathVariable Long id) {
        return foodService.getFoodById(id)
            .map(food -> {
                boolean inStock = food.getQuantity() > 0;
                InventoryResponse response = new InventoryResponse(
                    food.getId(),
                    food.getName(),
                    food.getQuantity(),
                    inStock
                );
                return ResponseEntity.ok(ApiResponse.success("Kiểm tra tồn kho thành công", response));
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure("Không tìm thấy món ăn với id: " + id, null)));
    }
    
    
    @PatchMapping("/edit/{id}/quantity")
    public ResponseEntity<ApiResponse<Food>> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        try {
            Food updatedFood = foodService.updateQuantity(id, quantity);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật số lượng thành công", updatedFood));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.failure("Lỗi khi cập nhật số lượng: " + e.getMessage(), null));
        }
    }
    
    public static class InventoryResponse {
        private Long id;
        private String name;
        private Integer quantity;
        private Boolean inStock;
        
        public InventoryResponse(Long id, String name, Integer quantity, Boolean inStock) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.inStock = inStock;
        }
        
        public Long getId() { return id; }
        public String getName() { return name; }
        public Integer getQuantity() { return quantity; }
        public Boolean getInStock() { return inStock; }
    }
}
