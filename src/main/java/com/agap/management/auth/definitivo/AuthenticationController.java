package com.agap.management.auth.definitivo;

import com.agap.management.auth.dto.AuthenticationRequestDTO;
import com.agap.management.auth.dto.AuthenticationResponseDTO;
import com.agap.management.auth.dto.RegisterRequestDTO;
import com.agap.management.auth.dto.RegisterResponseDTO;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = {"http://localhost:4200"}, originPatterns = {"*"})
@RequiredArgsConstructor
public class AuthenticationController {

    private final RegistrationServiceInterface registrationService;
    private final AuthenticationServiceInterface authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO request) {
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
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
