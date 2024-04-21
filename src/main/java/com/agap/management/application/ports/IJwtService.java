package com.agap.management.application.ports;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
