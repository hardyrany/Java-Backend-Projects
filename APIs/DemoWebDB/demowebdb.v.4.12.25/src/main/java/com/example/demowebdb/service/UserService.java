package com.example.demowebdb.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demowebdb.model.User;
import com.example.demowebdb.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return Optional.of(userRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {
        User user = findById(id).orElseThrow();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
    
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
