package com.agap.management.exceptions;

import com.agap.management.exceptions.personalizedException.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseExceptionDTO> handleAuthenticationException(AuthenticationException exception) {

        //CustomAuthenticationEntryPoint.ResponseDTO response = new CustomAuthenticationEntryPoint.ResponseDTO("Unauthorized", "Access denied due to invalid credentials xyz");
        ResponseExceptionDTO response = new ResponseExceptionDTO();
        response.setMessage(exception.getMessage()+ " echacatamente");
        response.setDescription(exception.getMessage()+ " echacatamente");
        response.setErrorCode(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ResponseExceptionDTO> handleUserAlreadyExistsException(UserAlreadyExistException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Register error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotEnabledYetException.class)
    public ResponseEntity<ResponseExceptionDTO> handleUserNotEnabledYetException(UserNotEnabledYetException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Login error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<ResponseExceptionDTO> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Verification error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VerificationTokenAlreadyExpiredException.class)
    public ResponseEntity<ResponseExceptionDTO> handleVerificationTokenAlreadyExpiredException(VerificationTokenAlreadyExpiredException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Verification error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ResponseExceptionDTO> handleInvalidTokenException(InvalidTokenException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Invalid token error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ChangePasswordException.class)
    public ResponseEntity<ResponseExceptionDTO> handleChangePasswordException(ChangePasswordException exception) {
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message("Password error")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.CONFLICT.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(EntityNotFoundByFieldException.class)
    public ResponseEntity<ResponseExceptionDTO> handleUserNotFoundByEmailException(EntityNotFoundByFieldException exception) {
        String entity = exception.getMessage().split(" ")[0];
        ResponseExceptionDTO response =  ResponseExceptionDTO.builder()
                .message( entity + " not found")
                .description(exception.getLocalizedMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /*@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication error: " + ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: " + ex.getMessage());
    }*/


}
