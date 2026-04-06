package com.iuh.fit.food_service.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateFoodRequest {
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private String category;
    private Integer quantity;
}
