package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.ChangePasswordRequestDTO;
import com.agap.management.domain.dtos.request.ResetPasswordRequestDTO;
import jakarta.mail.MessagingException;

import java.security.Principal;

public interface IUserService {
    String changePassword(ChangePasswordRequestDTO request, Principal connectedUser);
    String forgotPassword(String email) throws MessagingException;
    String resetPassword(String token, ResetPasswordRequestDTO request);
}
