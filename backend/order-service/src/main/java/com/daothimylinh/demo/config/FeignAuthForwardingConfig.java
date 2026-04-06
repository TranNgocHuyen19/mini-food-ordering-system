package com.daothimylinh.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthForwardingConfig {

	@Bean
	public ClientHttpRequestInterceptor bearerTokenForwardingInterceptor() {
		return (request, body, execution) -> {
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			if (!(requestAttributes instanceof ServletRequestAttributes servletAttributes)) {
				return execution.execute(request, body);
			}

			HttpServletRequest servletRequest = servletAttributes.getRequest();
			String authorizationHeader = servletRequest.getHeader("Authorization");
			if (authorizationHeader != null && !authorizationHeader.isBlank()) {
				request.getHeaders().set("Authorization", authorizationHeader);
			}

			return execution.execute(request, body);
		};
	}
}