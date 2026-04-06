package com.hoanghuy04.user_service.service;

import com.hoanghuy04.user_service.dto.AuthResponse;
import com.hoanghuy04.user_service.dto.LoginRequest;
import com.hoanghuy04.user_service.dto.RegisterRequest;
import com.hoanghuy04.user_service.dto.UserResponse;

import java.util.List;

public interface UserService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getCurrentUser();
}
