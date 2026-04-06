package com.daothimylinh.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private static final String FALLBACK_VALIDATE_PATH = "/api/users/validate";

    private final RestTemplate restTemplate;
    private final String userServiceUrl;
    private final String validateTokenPath;

    @Autowired
    public UserClient(
            RestTemplate restTemplate,
            @Value("${app.user-service.url}") String userServiceUrl,
            @Value("${app.user-service.validate-token-path}") String validateTokenPath
    ) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
        this.validateTokenPath = validateTokenPath;
    }

    public boolean validateToken(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authorizationHeader);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    userServiceUrl + validateTokenPath,
                    HttpMethod.GET,
                    request,
                    Void.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound notFound) {
            if (FALLBACK_VALIDATE_PATH.equals(validateTokenPath)) {
                throw notFound;
            }
            ResponseEntity<Void> fallbackResponse = restTemplate.exchange(
                    userServiceUrl + FALLBACK_VALIDATE_PATH,
                    HttpMethod.GET,
                    request,
                    Void.class
            );
            return fallbackResponse.getStatusCode().is2xxSuccessful();
        }
    }
}
