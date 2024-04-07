package com.agap.management.application.ports;

import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.dtos.RegisterResponseDTO;

public interface RegistrationServiceInterface {

    RegisterResponseDTO register(RegisterRequestDTO request);
    void verifyUser(String token);
}
