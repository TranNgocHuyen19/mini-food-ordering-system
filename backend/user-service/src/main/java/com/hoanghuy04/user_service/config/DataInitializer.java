package com.hoanghuy04.user_service.config;

import com.hoanghuy04.user_service.entity.Role;
import com.hoanghuy04.user_service.entity.User;
import com.hoanghuy04.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = User.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("Admin@1234"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("user@gmail.com")) {
            User user = User.builder()
                    .email("user@gmail.com")
                    .password(passwordEncoder.encode("User@1234"))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        }
    }
}
