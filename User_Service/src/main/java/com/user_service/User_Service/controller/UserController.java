package com.user_service.User_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.User_Service.entity.User;
import com.user_service.User_Service.repository.UserRepository;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET: Fetch all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // Check if the id is null (though it's unlikely in a valid URL)
        if (id == null) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request if id is null
        }
    
        // Attempt to find the user with the given id
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user)) // If found, return the user
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // If not found, return 404
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
