package com.mph.salelaptop.service;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}