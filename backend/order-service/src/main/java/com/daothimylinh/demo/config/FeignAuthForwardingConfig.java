package com.example.demo.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthForwardingConfig {

	@Bean
	public RequestInterceptor bearerTokenForwardingInterceptor() {
		return requestTemplate -> {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (!(requestAttributes instanceof ServletRequestAttributes servletAttributes)) {
				return;
			}

			HttpServletRequest request = servletAttributes.getRequest();
			String authorizationHeader = request.getHeader("Authorization");
			if (authorizationHeader != null && !authorizationHeader.isBlank()) {
				requestTemplate.header("Authorization", authorizationHeader);
			}
		};
	}
}