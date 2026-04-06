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
                // Seed sample food data
                foodRepository.save(Food.builder()
                    .name("Cơm Gà")
                    .price(35000.0)
                    .description("Cơm gà nướng chi tiết, gà nướng mềm thơm, cơm mềm dẻo")
                    .imageUrl("https://via.placeholder.com/300x300?text=Com+Ga")
                    .quantity(50)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Phở Bò")
                    .price(45000.0)
                    .description("Phở bò truyền thống, nước dùng đậm đà, noodle mềm")
                    .imageUrl("https://via.placeholder.com/300x300?text=Pho+Bo")
                    .quantity(40)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bánh Mì")
                    .price(20000.0)
                    .description("Bánh mì thơm giòn, nhân giò xối mỡ, nước sốt cay")
                    .imageUrl("https://via.placeholder.com/300x300?text=Banh+Mi")
                    .quantity(60)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cơm Chiên")
                    .price(40000.0)
                    .description("Cơm chiên tỏi, có trứng, kiểu Thái")
                    .imageUrl("https://via.placeholder.com/300x300?text=Com+Chien")
                    .quantity(45)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Mì Xào")
                    .price(35000.0)
                    .description("Mì xào có hốc, ít dầu, nhiều rau")
                    .imageUrl("https://via.placeholder.com/300x300?text=Mi+Xao")
                    .quantity(55)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Cơm Sườn")
                    .price(50000.0)
                    .description("Cơm sườn nướng, sườn mềm, nước màu đậm đà")
                    .imageUrl("https://via.placeholder.com/300x300?text=Com+Suon")
                    .quantity(35)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Bún Chả")
                    .price(40000.0)
                    .description("Bún chả Hà Nội, charcoal grilled pork")
                    .imageUrl("https://via.placeholder.com/300x300?text=Bun+Cha")
                    .quantity(48)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Canh Chua")
                    .price(30000.0)
                    .description("Canh chua cá, chua chua, có rau")
                    .imageUrl("https://via.placeholder.com/300x300?text=Canh+Chua")
                    .quantity(42)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Chè Ba Màu")
                    .price(15000.0)
                    .description("Chè ba màu lạnh, đậu đỏ, khoai môn, sắn")
                    .imageUrl("https://via.placeholder.com/300x300?text=Che+Ba+Mau")
                    .quantity(70)
                    .build());
                
                foodRepository.save(Food.builder()
                    .name("Trà Đen")
                    .price(10000.0)
                    .description("Trà đen lạnh, đá, ngọt vừa")
                    .imageUrl("https://via.placeholder.com/300x300?text=Tra+Den")
                    .quantity(80)
                    .build());
                
                System.out.println("✅ Database initialized with sample food data!");
            }
        };
    }
}
