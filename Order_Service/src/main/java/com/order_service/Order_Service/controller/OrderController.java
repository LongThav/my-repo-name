package com.order_service.Order_Service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.order_service.Order_Service.dto.OrderWithUserDTO;
import com.order_service.Order_Service.entity.Order;
import com.order_service.Order_Service.entity.User;
import com.order_service.Order_Service.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate; // Inject the RestTemplate

    // URL of the UserService (assumes the UserService exposes an endpoint like
    // /users/{id})
    private final String USER_SERVICE_URL = "http://localhost:8081/users/";

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        // Call the UserService to check if the user exists
        ResponseEntity<User> userResponse = restTemplate.getForEntity(USER_SERVICE_URL + order.getUserId(), User.class);

        if (userResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
        }

        // User exists, create the order
        order.setOrderStatus("Pending");
        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/users-with-orders")
    public ResponseEntity<List<OrderWithUserDTO>> getUsersWithOrders() {
        // Fetch all orders with userId
        List<Order> orders = orderRepository.findAllOrdersWithUser();

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build(); // No orders found
        }

        // Create a list to hold the DTOs
        List<OrderWithUserDTO> orderWithUserDTOList = new ArrayList<>();

        // Fetch users for each userId and map to OrderWithUserDTO
        for (Order order : orders) {
            try {
                // Make an API call to the User service to get user details for each order
                ResponseEntity<User> userResponse = restTemplate.getForEntity(USER_SERVICE_URL + order.getUserId(),
                        User.class);

                if (userResponse.getStatusCode().is2xxSuccessful()) {
                    User user = userResponse.getBody();
                    OrderWithUserDTO orderWithUserDTO = new OrderWithUserDTO(order, user);
                    orderWithUserDTOList.add(orderWithUserDTO); // Add the DTO to the list
                }
            } catch (Exception e) {
                // Handle exception if user not found or other errors
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok(orderWithUserDTOList); // Return the list of DTOs
    }
}
