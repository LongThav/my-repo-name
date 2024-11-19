package com.order_service.Order_Service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.order_service.Order_Service.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fetch all distinct userIds who have placed orders
    @Query("SELECT o FROM Order o WHERE o.userId IS NOT NULL")
    List<Order> findAllOrdersWithUser();  // Get all orders with userId not null
}