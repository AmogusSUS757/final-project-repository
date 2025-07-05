package com.example.eshopru.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") 
    private String secret;

    @Value("${jwt.expiration-ms}") 
    private long expirationMs;
    
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();
    
    public void invalidateToken(String token) {
    	invalidatedTokens.add(token);
    	System.out.println(invalidatedTokens);
    	}
    
    public boolean isTokenInvalidate(String token) {
    	return invalidatedTokens.contains(token);
    }


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) 
                .setIssuedAt(new Date()) 
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) 
                .signWith(getSigningKey())
                .compact();
    }


    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

 
    public boolean validateToken(String token) {
        try {
            getClaims(token); 
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

  
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) 
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
