package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IAuthenticationService;
import com.agap.management.application.ports.IRegistrationService;
import com.agap.management.domain.dtos.LoginRequestDTO;
import com.agap.management.domain.dtos.LoginResponseDTO;
import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IRegistrationService registrationService;
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) throws MessagingException {
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<LoginResponseDTO> verifyUser(@PathVariable String token) throws MessagingException {
        return ResponseEntity.ok(registrationService.verifyUser(token));
        //throw new RuntimeException("LOCAL EXCEPTION");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
