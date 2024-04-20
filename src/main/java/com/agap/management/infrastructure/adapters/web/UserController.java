package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import com.agap.management.application.services.UserService;
import com.agap.management.domain.dtos.ForgotPasswordDTO;
import com.agap.management.domain.dtos.ResetPasswordRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request, Principal connectedUser) {
        System.out.println("ENDPOINT DE CHANGE_PASSWORD WAS CALLED: ");
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();  // 202
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordDTO request) throws MessagingException {
        System.out.println("ENDPOINT DE FORGOT_PASSWORD WAS CALLED: ");
        return ResponseEntity.ok(userService.forgotPassword(request.getEmail()));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request, @RequestHeader("Authorization") String token) {
        System.out.println("ENDPOINT DE RESET_PASSWORD WAS CALLED: ");
        String message = userService.resetPassword(token, request);
        return ResponseEntity.ok(Map.of("message", message));
    }

}
