package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import com.agap.management.domain.dtos.ControllerResponseDTO;
import com.agap.management.domain.dtos.ForgotPasswordDTO;
import com.agap.management.domain.dtos.ResetPasswordRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/change-password")
    public ResponseEntity<ControllerResponseDTO> changePassword(@RequestBody @Valid ChangePasswordRequestDTO request, Principal connectedUser) {
        String message = userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().body(new ControllerResponseDTO(message));  // 202
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ControllerResponseDTO> forgotPassword(@RequestBody @Valid ForgotPasswordDTO request) throws MessagingException {
        String message = userService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(new ControllerResponseDTO(message));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ControllerResponseDTO> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO request, @RequestHeader("Authorization") String token) {
        String message = userService.resetPassword(token, request);
        return ResponseEntity.ok(new ControllerResponseDTO(message));
    }

}
