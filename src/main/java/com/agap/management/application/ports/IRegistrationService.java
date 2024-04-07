package com.agap.management.application.ports;

import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import jakarta.mail.MessagingException;

public interface IRegistrationService {

    RegisterResponseDTO register(RegisterRequestDTO request) throws MessagingException;
    void verifyUser(String token) throws MessagingException;
}
