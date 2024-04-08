package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import com.agap.management.application.services.UserService;
import com.agap.management.domain.dtos.ForgotPasswordDTO;
import com.agap.management.domain.dtos.ResetPasswordRequestDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestDTO request, Principal connectedUser) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();  // 202
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDTO request) throws MessagingException {
        return ResponseEntity.ok(userService.forgotPassword(request.getEmail()));
    }

    @PutMapping("/reset-password/{token}")
    public ResponseEntity<String> resetPassword(
            @PathVariable String token,
            //@RequestParam String email,
            @RequestBody ResetPasswordRequestDTO request) {
        return ResponseEntity.ok(userService.resetPassword(token, request));
    }

}
