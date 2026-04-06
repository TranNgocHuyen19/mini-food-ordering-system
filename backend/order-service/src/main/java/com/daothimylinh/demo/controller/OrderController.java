package com.daothimylinh.demo.controller;

import com.daothimylinh.demo.dto.ApiResponse;
import com.daothimylinh.demo.dto.CreateOrderRequest;
import com.daothimylinh.demo.dto.OrderItemRequest;
import com.daothimylinh.demo.dto.OrderItemResponse;
import com.daothimylinh.demo.dto.OrderResponse;
import com.daothimylinh.demo.dto.UpdateOrderStatusRequest;
import com.daothimylinh.demo.model.Order;
import com.daothimylinh.demo.model.OrderItem;
import com.daothimylinh.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody UpdateOrderStatusRequest request
    ) {
        Order updated = orderService.updateOrderStatus(id, request.status());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("Order status updated", toOrderResponse(updated)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> data = orderService.getAllOrders().stream().map(this::toOrderResponse).toList();
        return ResponseEntity.ok(ApiResponse.success("Orders fetched", data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateOrderRequest request
    ) {
        Order created = orderService.createOrder(toOrder(request), authorizationHeader);
        return ResponseEntity.ok(ApiResponse.success("Order created", toOrderResponse(created)));
    }

    private Order toOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.userId());

        List<OrderItem> items = new ArrayList<>();
        if (request.items() != null) {
            for (OrderItemRequest itemRequest : request.items()) {
                OrderItem item = new OrderItem();
                item.setFoodId(itemRequest.foodId());
                item.setQuantity(itemRequest.quantity());
                items.add(item);
            }
        }
        order.setItems(items);
        return order;
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = new ArrayList<>();
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                itemResponses.add(new OrderItemResponse(
                        item.getFoodId(),
                        item.getFoodName(),
                        item.getQuantity(),
                        item.getPrice()
                ));
            }
        }

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                itemResponses,
                order.getStatus()
        );
    }
}
