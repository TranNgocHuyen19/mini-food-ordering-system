package com.daothimylinh.demo.repository;

import com.daothimylinh.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
