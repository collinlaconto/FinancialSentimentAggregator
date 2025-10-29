package com.financialnews.sentiment_aggregator.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.financialnews.sentiment_aggregator.model.User;
import com.financialnews.sentiment_aggregator.repository.UserRepository;
import com.financialnews.sentiment_aggregator.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;


    protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response, 
    FilterChain filterChain
    ) throws ServletException, IOException {
        // Start JWT filter
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // Everything after "Bearer "
            if (jwtUtil.validateToken(token)) {
                // Token is valid - extract username
                String username = jwtUtil.extractUsername(token);
                // Valid token
               

                    Optional<User> userOptional = userRepository.findByUsername(username);
                    if (userOptional.isPresent()) {
                        User user = userOptional.get();
                        // Create an Authentication object
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(
                                user,             // principal (the user)
                                null,             // credentials (null for JWT - we already authenticated)
                                new ArrayList<>() // authorities/roles (empty for now)
                            );
                        // Put it in Spring Security's context
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        // Authentication set
                        filterChain.doFilter(request, response);
                        return;
                    }
                    else {
                        // Username is invalid - don't authenticate
                        filterChain.doFilter(request, response);
                        return;
                    }
            }
            else {
                // Token is invalid - don't authenticate
                filterChain.doFilter(request, response);
                return;
            }
        } 
        else {
            // No valid token, continue filter chain without authentication
            filterChain.doFilter(request, response);
            return;
        }
    }
}
