package com.hoanghuy04.user_service.controller;

import com.hoanghuy04.user_service.dto.ApiResponse;
import com.hoanghuy04.user_service.dto.AuthResponse;
import com.hoanghuy04.user_service.dto.LoginRequest;
import com.hoanghuy04.user_service.dto.RegisterRequest;
import com.hoanghuy04.user_service.dto.UserResponse;
import com.hoanghuy04.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Register successful", userService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", userService.login(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success("Get all users successful", userService.getAllUsers()));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseEntity.ok(ApiResponse.success("Get current user successful", userService.getCurrentUser()));
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken() {
        // If the request reaches here, it means the JWT token in the header was valid
        // thanks to the JwtAuthenticationFilter.
        return ResponseEntity.ok(ApiResponse.success("Token is valid", true));
    }
}
