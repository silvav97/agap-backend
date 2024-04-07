package com.agap.management.exception;

import com.agap.management.auth.CustomAuthenticationEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomAuthenticationEntryPoint.ResponseDTO> handleAuthenticationException(AuthenticationException exception) {
        CustomAuthenticationEntryPoint.ResponseDTO response = new CustomAuthenticationEntryPoint.ResponseDTO("Unauthorized", "Access denied due to invalid credentials xyz");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
