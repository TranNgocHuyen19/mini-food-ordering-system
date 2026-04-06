package com.daothimylinh.demo.config.security;

import com.daothimylinh.demo.dto.ApiResponse;
import com.daothimylinh.demo.service.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	private final UserClient userClient;
	private final ObjectMapper objectMapper;
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	public TokenAuthenticationFilter(UserClient userClient, ObjectMapper objectMapper) {
		this.userClient = userClient;
		this.objectMapper = objectMapper;
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		return HttpMethod.OPTIONS.matches(request.getMethod())
				|| pathMatcher.match("/h2-console/**", servletPath)
				|| pathMatcher.match("/actuator/health", servletPath)
				|| pathMatcher.match("/error", servletPath);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		log.debug("Processing authentication. method={} path={} token={}", request.getMethod(), request.getRequestURI(), authorizationHeader);
		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			log.warn("Auth rejected: missing/invalid Authorization header. method={} path={}",
					request.getMethod(), request.getRequestURI());
			writeUnauthorized(response, "Missing or invalid Authorization header");
			return;
		}

		boolean validToken;
		try {
			log.info("Validating bearer token. method={} path={}", request.getMethod(), request.getRequestURI());
			validToken = userClient.validateToken(authorizationHeader);
		} catch (Exception exception) {
			log.error("Token validation failed due to upstream error. method={} path={} message={}",
					request.getMethod(), request.getRequestURI(), exception.getMessage(), exception);
			writeUnauthorized(response, "Unable to validate token");
			return;
		}

		if (!validToken) {
			log.warn("Token validation result: invalid token. method={} path={}",
					request.getMethod(), request.getRequestURI());
			writeUnauthorized(response, "Token is invalid");
			return;
		}

		log.info("Token validated successfully. method={} path={}", request.getMethod(), request.getRequestURI());

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