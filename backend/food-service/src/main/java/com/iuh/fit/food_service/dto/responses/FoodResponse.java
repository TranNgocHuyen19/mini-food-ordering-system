package com.iuh.fit.food_service.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private String category;
    private Integer quantity;
}
