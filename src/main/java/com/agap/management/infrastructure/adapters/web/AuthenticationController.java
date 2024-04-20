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
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO request) throws MessagingException {
        System.out.println("ENDPOINT DE REGISTER WAS CALLED: ");
        return ResponseEntity.ok(registrationService.register(request));
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<LoginResponseDTO> verifyUser(@PathVariable String token) throws MessagingException {
        System.out.println("ENDPOINT DE VERIFY_USER WAS CALLED: ");
        return ResponseEntity.ok(registrationService.verifyUser(token));
        //throw new RuntimeException("LOCA EXCEPTION");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        System.out.println("ENDPOINT DE LOGIN WAS CALLED: ");
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("ENDPOINT DE REFRESH_TOKEN WAS CALLED: ");
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Mi Refresh Token es: " + refreshToken);

        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}
