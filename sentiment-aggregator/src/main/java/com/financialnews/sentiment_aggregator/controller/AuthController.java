package com.financialnews.sentiment_aggregator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financialnews.sentiment_aggregator.dto.LoginRequest;
import com.financialnews.sentiment_aggregator.dto.RegisterRequest;
import com.financialnews.sentiment_aggregator.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String postRegister(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public String postLogin(@RequestBody LoginRequest loginRequest) {

        return authService.login(loginRequest);
    }
}
