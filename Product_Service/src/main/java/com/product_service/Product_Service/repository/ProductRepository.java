package com.product_service.Product_Service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product_service.Product_Service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}