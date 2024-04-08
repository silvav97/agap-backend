package com.agap.management.exceptions;

import com.agap.management.exceptions.personalizedException.ResponseExceptionDTO;
import com.agap.management.exceptions.personalizedException.UserAlreadyExistException;
import com.agap.management.infrastructure.security.CustomAuthenticationEntryPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseExceptionDTO> handleAuthenticationException(AuthenticationException exception) {

        //CustomAuthenticationEntryPoint.ResponseDTO response = new CustomAuthenticationEntryPoint.ResponseDTO("Unauthorized", "Access denied due to invalid credentials xyz");
        ResponseExceptionDTO response = new ResponseExceptionDTO();
        response.setMessage(exception.getMessage());
        response.setDescription(exception.getMessage());
        response.setErrorCode(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ResponseExceptionDTO> handleUserAlreadyExists(UserAlreadyExistException exception) {

        /*ResponseExceptionDTO response =  new ResponseExceptionDTO();
        response.setMessage(exception.getLocalizedMessage());
        response.setDescription(exception.getMessage());
        response.setErrorCode(HttpStatus.CONFLICT.value());*/

        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Register error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
