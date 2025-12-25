package com.example.demowebdb.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import com.example.demowebdb.dto.UserDTO;
import com.example.demowebdb.model.User;
import com.example.demowebdb.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /users → retorna lista de UserDTO
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.findAll().stream()
            .map(userService::toDTO)
            .toList();
    }

    // GET /users/{id} → retorna UserDTO específico
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userService.toDTO(user);
    }

    // POST /users → recebe UserDTO e retorna UserDTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.fromDTO(userDTO);
        userService.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        });
        User savedUser = userService.create(user);
        return userService.toDTO(savedUser);
    }

    // PATH /users/{id} - atualizare user
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        User user = userService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getEmail() != null) {
                userService.findByEmail(updatedUser.getEmail()).ifPresent(u -> {
                    if (!u.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
                    }
                });
                user.setEmail(updatedUser.getEmail());
            }
        User savedUser = userService.update(id, user);
            return userService.toDTO(savedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userService.deleteById(id);
    }
}
