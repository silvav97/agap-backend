package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IAuthenticationService;
import com.agap.management.application.ports.IRegistrationService;
import com.agap.management.domain.dtos.AuthenticationRequestDTO;
import com.agap.management.domain.dtos.AuthenticationResponseDTO;
import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IRegistrationService   registrationService;
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) throws MessagingException {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Mi Refresh Token es: " + refreshToken);

        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        try {
            registrationService.verifyUser(token);
            return ResponseEntity.ok("Account successfully verified.");
        } catch (RuntimeException | MessagingException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
