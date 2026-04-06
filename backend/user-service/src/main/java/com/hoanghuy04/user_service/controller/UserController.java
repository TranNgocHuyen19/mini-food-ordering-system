package com.hoanghuy04.user_service.controller;

import com.hoanghuy04.user_service.dto.ApiResponse;
import com.hoanghuy04.user_service.dto.AuthResponse;
import com.hoanghuy04.user_service.dto.LoginRequest;
import com.hoanghuy04.user_service.dto.RegisterRequest;
import com.hoanghuy04.user_service.dto.UserResponse;
import com.hoanghuy04.user_service.util.CookieUtil;
import com.hoanghuy04.user_service.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CookieUtil cookieUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@jakarta.validation.Valid @RequestBody RegisterRequest request,
            HttpServletResponse response) {
        AuthResponse authResponse = userService.register(request);
        cookieUtil.createCookie(response, "jwtToken", authResponse.getToken(), 86400);
        return ResponseEntity.ok(ApiResponse.success("Register successful", authResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@jakarta.validation.Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {
        AuthResponse authResponse = userService.login(request);
        cookieUtil.createCookie(response, "jwtToken", authResponse.getToken(), 86400);
        return ResponseEntity.ok(ApiResponse.success("Login successful", authResponse));
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
        return ResponseEntity.ok(ApiResponse.success("Token is valid", true));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
        cookieUtil.clearCookie(response, "jwtToken");
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }
}
