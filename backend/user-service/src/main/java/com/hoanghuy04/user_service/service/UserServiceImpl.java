package com.hoanghuy04.user_service.service;

import com.hoanghuy04.user_service.dto.AuthResponse;
import com.hoanghuy04.user_service.dto.LoginRequest;
import com.hoanghuy04.user_service.dto.RegisterRequest;
import com.hoanghuy04.user_service.dto.UserResponse;
import com.hoanghuy04.user_service.entity.Role;
import com.hoanghuy04.user_service.entity.User;
import com.hoanghuy04.user_service.repository.UserRepository;
import com.hoanghuy04.user_service.security.CustomUserDetails;
import com.hoanghuy04.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER) // Default role
                .build();
        userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(new CustomUserDetails(user));
        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String jwtToken = jwtUtil.generateToken(new CustomUserDetails(user));
        return new AuthResponse(jwtToken);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .role(user.getRole())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getCurrentUser() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
