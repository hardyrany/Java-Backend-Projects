package com.example.demowebdb.controller;

import com.example.demowebdb.model.User;
import com.example.demowebdb.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        });
        return userRepository.save(user);
    }
}
