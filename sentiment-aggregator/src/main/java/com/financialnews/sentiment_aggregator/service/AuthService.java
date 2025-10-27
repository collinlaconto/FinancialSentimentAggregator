package com.financialnews.sentiment_aggregator.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.financialnews.sentiment_aggregator.dto.LoginRequest;
import com.financialnews.sentiment_aggregator.dto.RegisterRequest;
import com.financialnews.sentiment_aggregator.model.User;
import com.financialnews.sentiment_aggregator.repository.UserRepository;
import com.financialnews.sentiment_aggregator.util.JwtUtil;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil token;

    public String register(RegisterRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        repository.save(user);
        String userToken = token.generateToken(user.getUsername());
        return userToken;
        
    }

    public String login(LoginRequest request) {

        Optional<User> userOptional = repository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();  // Gets the User inside the Optional
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String userToken = token.generateToken(user.getUsername());
                return userToken;
            }
            else {
                throw new RuntimeException("Invalid username or password");
            }
        } 
        else {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
