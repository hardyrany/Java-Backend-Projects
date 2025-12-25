package com.example.demoweb.controller;

import com.example.demoweb.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        // Database
        // Return Request
        user.setId(1L); // Simulation generate ID
        return user;
    }
}
