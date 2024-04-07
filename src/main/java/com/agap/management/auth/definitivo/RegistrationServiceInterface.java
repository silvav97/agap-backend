package com.agap.management.auth.definitivo;

import com.agap.management.auth.dto.RegisterRequestDTO;
import com.agap.management.auth.dto.RegisterResponseDTO;

public interface RegistrationServiceInterface {

    RegisterResponseDTO register(RegisterRequestDTO request);
    void verifyUser(String token);
}
