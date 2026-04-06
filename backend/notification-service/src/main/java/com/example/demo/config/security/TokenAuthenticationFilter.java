package com.example.demo.config.security;

import com.example.demo.client.UserServiceClient;
import com.example.demo.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final UserServiceClient userServiceClient;
	private final ObjectMapper objectMapper;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public TokenAuthenticationFilter(UserServiceClient userServiceClient, ObjectMapper objectMapper) {
		this.userServiceClient = userServiceClient;
		this.objectMapper = objectMapper;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		return pathMatcher.match("/h2-console/**", servletPath)
				|| pathMatcher.match("/actuator/health", servletPath)
				|| pathMatcher.match("/error", servletPath);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			writeUnauthorized(response, "Missing or invalid Authorization header");
			return;
		}

		ApiResponse<Boolean> tokenValidationResponse;
		try {
			tokenValidationResponse = userServiceClient.validateToken();
		} catch (Exception exception) {
			writeUnauthorized(response, "Unable to validate token");
			return;
		}

		if (tokenValidationResponse == null
				|| !tokenValidationResponse.success()
				|| tokenValidationResponse.data() == null
				|| !tokenValidationResponse.data()) {
			writeUnauthorized(response, "Token is invalid");
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken("authenticated-user", null, Collections.emptyList());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

	private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ApiResponse<Object> body = ApiResponse.failure(message, null);
		response.getWriter().write(objectMapper.writeValueAsString(body));
	}
}