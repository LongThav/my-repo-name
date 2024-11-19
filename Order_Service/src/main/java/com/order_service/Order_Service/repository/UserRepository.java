package com.order_service.Order_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order_service.Order_Service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}