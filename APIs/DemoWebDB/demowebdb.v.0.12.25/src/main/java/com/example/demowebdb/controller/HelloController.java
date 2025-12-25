package com.example.demowebdb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "Hello Backend Engineer!, Welcome to the world of APIs! /nThis is your first Spring Boot API. /nWith Database Integration!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from /hello endpoint API!";
    }
}
