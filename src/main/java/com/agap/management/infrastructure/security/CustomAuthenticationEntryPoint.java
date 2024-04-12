package com.agap.management.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {


        //response.setStatus(HttpStatus.UNAUTHORIZED.value());
        if (authException != null) {
            response.setStatus(HttpStatus.FORBIDDEN.value()); // Cambiar si es necesario, según el caso
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResponseDTO responseDTO = new ResponseDTO("Unauthorized", "Access denied. " + authException.getMessage());
        String jsonResponse = new ObjectMapper().writeValueAsString(responseDTO);
        response.getWriter().write(jsonResponse);
    }

    @Data
    //@Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseDTO {
        private String error;
        private String message;
    }
}



