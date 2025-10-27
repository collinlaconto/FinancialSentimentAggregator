package com.financialnews.sentiment_aggregator.util;

import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private String secretKey = "myVerySecretKeyThatIsAtLeast32CharactersLongForHS256Algorithm";

    public String generateToken(String username) {

        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86400000); // 24 hours in milliseconds
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        String token = Jwts.builder()
            .setSubject(username)                           // Who is this token for?
            .setIssuedAt(now)                               // When was it created?
            .setExpiration(expiration)                      // When does it expire?
            .signWith(key, SignatureAlgorithm.HS256)        // Sign it with secret key
            .compact();  
        return token;
    }

    public Boolean validateToken(String token) {
        try {
            extractUsername(token);  // If this succeeds, token is valid
            return true;
        } catch (Exception e) {
            return false;  // If it throws exception, token is invalid
        }
    }

    public String extractUsername(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)           // Key to verify signature
            .build()
            .parseClaimsJws(token)
            .getBody();                    // Get the payload/claims

        return claims.getSubject();
    }

}
