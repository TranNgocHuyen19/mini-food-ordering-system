package com.iuh.fit.food_service;

import com.iuh.fit.food_service.model.Food;
import com.iuh.fit.food_service.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initDatabase(FoodRepository foodRepository) {
        return args -> {
            // Check if data already exists
            if (foodRepository.count() == 0) {
                // Seed sample food data with Vietnamese dishes
                foodRepository.save(Food.builder()
                    .name("Cơm Tấm")
                    .price(45000.0)
                    .description("Cơm tấm nóng hổi, thịt nướng thơm, trứng ốp")
                    .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500")
                    .category("Cơm")
                    .quantity(50)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bún Bò Huế")
                    .price(55000.0)
                    .description("Bún bò Huế đặc biệt, nước dùng đậm đà, thịt bò mềm")
                    .imageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=500")
                    .category("Bún Mì")
                    .quantity(40)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Gỏi Cuốn")
                    .price(35000.0)
                    .description("Gỏi cuốn tươi, tôm, thịt lợn, rau thơm")
                    .imageUrl("https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=500")
                    .category("Cuốn")
                    .quantity(60)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Mì Quảng")
                    .price(48000.0)
                    .description("Mì Quảng truyền thống, nước ngon, rau sống")
                    .imageUrl("https://images.unsplash.com/photo-1612874742237-6526221588e3?w=500")
                    .category("Bún Mì")
                    .quantity(45)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cơm Chiên Dương Châu")
                    .price(50000.0)
                    .description("Cơm chiên Dương Châu, tôm, thịt, trứng")
                    .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500")
                    .category("Cơm")
                    .quantity(50)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bánh Chưng")
                    .price(30000.0)
                    .description("Bánh chưng truyền thống, thịt đặc biệt, vị tết")
                    .imageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=500")
                    .category("Bánh")
                    .quantity(55)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Xôi Gấc")
                    .price(25000.0)
                    .description("Xôi gấc đỏ tươi, gấc tự nhiên, dứa")
                    .imageUrl("https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=500")
                    .category("Xôi")
                    .quantity(40)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Chim Cút Nướng")
                    .price(65000.0)
                    .description("Chim cút nướng, da giòn, thịt mềm, gia vị thơm")
                    .imageUrl("https://images.unsplash.com/photo-1612874742237-6526221588e3?w=500")
                    .category("Nướng")
                    .quantity(35)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cơm Sườn Nướng")
                    .price(60000.0)
                    .description("Cơm sườn đặc biệt, sườn nướng siêu mềm")
                    .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500")
                    .category("Cơm")
                    .quantity(45)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bánh Mỳ Thối")
                    .price(20000.0)
                    .description("Bánh mỳ giòn, công thức truyền thống, nhân hấp dẫn")
                    .imageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=500")
                    .category("Bánh")
                    .quantity(70)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Phở Tái Nạm")
                    .price(50000.0)
                    .description("Phở bò tái nạm, nước dùng thơm, bánh phở mềm")
                    .imageUrl("https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=500")
                    .category("Phở")
                    .quantity(50)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cơm Cà Ri Gà")
                    .price(55000.0)
                    .description("Cơm cà ri gà, cà ri thơm, gà mềm, rau cải")
                    .imageUrl("https://images.unsplash.com/photo-1612874742237-6526221588e3?w=500")
                    .category("Cơm")
                    .quantity(42)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Tôm Hùm Nướng Bơ")
                    .price(120000.0)
                    .description("Tôm hùm tươi, nướng bơ tỏi, siêu ngon")
                    .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500")
                    .category("Hải Sản")
                    .quantity(25)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cua Cà Ri")
                    .price(85000.0)
                    .description("Cua biển cà ri, nước sốt đậm đà, cua thơm")
                    .imageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=500")
                    .category("Hải Sản")
                    .quantity(30)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Vịt Quay Bắc Kinh")
                    .price(75000.0)
                    .description("Vịt quay, da răng, thịt mềm, bánh mặc ong")
                    .imageUrl("https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=500")
                    .category("Nướng")
                    .quantity(35)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Lẩu Hải Sản")
                    .price(95000.0)
                    .description("Lẩu hải sản tươi, tôm, cua, mực, nước dùng")
                    .imageUrl("https://images.unsplash.com/photo-1612874742237-6526221588e3?w=500")
                    .category("Hải Sản")
                    .quantity(40)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bò Cuốn Lá Lốt")
                    .price(45000.0)
                    .description("Bò cuốn lá lốt, nướng than, nước chấm chua cay")
                    .imageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=500")
                    .category("Cuốn")
                    .quantity(50)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Chả Cá Lã Vọng")
                    .price(65000.0)
                    .description("Chả cá lã vọng, cá tươi, topping phong phú")
                    .imageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=500")
                    .category("Nướng")
                    .quantity(38)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Canh Cua Cà Chua")
                    .price(40000.0)
                    .description("Canh cua cà chua chua cay, cua tươi, tôm")
                    .imageUrl("https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=500")
                    .category("Hải Sản")
                    .quantity(48)
                    .build());

                
                System.out.println("✅ Database initialized with 20 Vietnamese food items!");
            }
        };
    }
}
