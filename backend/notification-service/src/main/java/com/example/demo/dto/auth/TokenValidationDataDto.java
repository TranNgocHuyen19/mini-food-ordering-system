package com.example.demo.dto.auth;

import java.util.List;

public record TokenValidationDataDto(
		boolean valid,
		Long userId,
		String username,
		List<String> roles) {
}