package com.user_service.User_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user_service.User_Service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}