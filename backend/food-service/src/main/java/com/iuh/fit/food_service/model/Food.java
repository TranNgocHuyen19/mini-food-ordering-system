package com.iuh.fit.food_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double price;
    
    @Column
    private String description;
    
    @Column
    private String imageUrl;
    
    @Column
    private String category;
    
    @Column(nullable = false)
    private Integer quantity = 0;
}
