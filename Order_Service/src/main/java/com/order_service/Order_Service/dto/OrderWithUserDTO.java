package com.order_service.Order_Service.dto;

import com.order_service.Order_Service.entity.Order;
import com.order_service.Order_Service.entity.User;

public class OrderWithUserDTO {
    private Long id;
    private String productName;
    private int quantity;
    private Double productPrice;
    private String orderStatus;
    private Long userId;
    private User user; // Add the user information here

    public OrderWithUserDTO(Order order, User user) {
        this.id = order.getId();
        this.productName = order.getProductName();
        this.quantity = order.getQuantity();
        this.productPrice = order.getProductPrice();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUserId();
        this.user = user; // Set user information
    }

    // Getters and Setters for the fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
