package com.agap.management.application.ports;

import com.agap.management.domain.dtos.response.LoginResponseDTO;
import com.agap.management.domain.dtos.request.RegisterRequestDTO;
import com.agap.management.domain.dtos.response.RegisterResponseDTO;
import jakarta.mail.MessagingException;

public interface IRegistrationService {
    RegisterResponseDTO register(RegisterRequestDTO request) throws MessagingException;
    LoginResponseDTO verifyUser(String token) throws MessagingException;
}
