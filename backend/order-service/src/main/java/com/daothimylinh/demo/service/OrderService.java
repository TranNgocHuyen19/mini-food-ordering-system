package com.daothimylinh.demo.service;

import com.daothimylinh.demo.model.Order;
import com.daothimylinh.demo.model.OrderItem;
import com.daothimylinh.demo.repository.OrderRepository;
import com.daothimylinh.demo.model.OrderStatus;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final FoodClient foodClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserClient userClient, FoodClient foodClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.foodClient = foodClient;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @CircuitBreaker(name = "default", fallbackMethod = "createOrderFallback")
    @Retry(name = "default", fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "default", fallbackMethod = "createOrderFallback")
    public Order createOrder(Order order, String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing Authorization header");
        }

        boolean validToken;
        try {
            validToken = userClient.validateToken(authorizationHeader);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is invalid or User Service unavailable");
        }

        if (!validToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is invalid");
        }

        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order items must not be empty");
        }

        for (OrderItem item : order.getItems()) {
            if (item.getFoodId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "foodId is required");
            }
            if (item.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity must be greater than 0");
            }

            Map<String, Object> food;
            try {
                food = foodClient.getFoodById(item.getFoodId());
            } catch (FeignException.NotFound ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food not found: " + item.getFoodId());
            } catch (FeignException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Food Service unavailable");
            }

            if (food == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food not found: " + item.getFoodId());
            }

            Object name = food.get("name") != null ? food.get("name") : food.get("foodName");
            Object price = food.get("price") != null ? food.get("price") : food.get("cost");

            if (name != null) {
                item.setFoodName(String.valueOf(name));
            }
            if (price != null) {
                try {
                    item.setPrice(Double.parseDouble(String.valueOf(price)));
                } catch (NumberFormatException ex) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Invalid food price from Food Service for foodId: " + item.getFoodId());
                }
            }
        }

        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }

        order.attachItemsToOrder();

        return orderRepository.save(order);
    }

    public Order createOrderFallback(Order order, String authorizationHeader, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Dependent services are currently down, please try again later. Reason: " + t.getMessage());
    }


    public Order updateOrderStatus(Long id, OrderStatus status) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                })
                .orElse(null);
    }
}
