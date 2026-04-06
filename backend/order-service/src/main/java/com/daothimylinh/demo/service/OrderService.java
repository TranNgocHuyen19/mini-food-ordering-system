package com.daothimylinh.demo.service;

import com.daothimylinh.demo.model.Order;
import com.daothimylinh.demo.model.OrderItem;
import com.daothimylinh.demo.repository.OrderRepository;
import com.daothimylinh.demo.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

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

            Map<String, Object> food = foodClient.getFoodById(item.getFoodId());
            if (food == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food not found: " + item.getFoodId());
            }

            Object name = food.get("name") != null ? food.get("name") : food.get("foodName");
            Object price = food.get("price") != null ? food.get("price") : food.get("cost");

            if (name != null) {
                item.setFoodName(String.valueOf(name));
            }
            if (price != null) {
                item.setPrice(Double.parseDouble(String.valueOf(price)));
            }
        }

        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }

        order.attachItemsToOrder();

        return orderRepository.save(order);
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
