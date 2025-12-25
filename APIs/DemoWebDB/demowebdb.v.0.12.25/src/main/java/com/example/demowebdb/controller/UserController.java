package com.example.demowebdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demowebdb.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    // Lista para guardar usuários em memória
    private final List<User> users = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    // GET /users → lista todos os usuários
    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    // POST /users → cria usuário
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        user.setId(counter.getAndIncrement()); // gera ID automático
        users.add(user); // adiciona na lista
        return user; // retorna JSON com o usuário criado
    }
}
