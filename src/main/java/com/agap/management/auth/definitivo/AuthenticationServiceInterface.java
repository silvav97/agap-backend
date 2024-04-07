package com.agap.management.auth.definitivo;

import com.agap.management.auth.dto.AuthenticationRequestDTO;
import com.agap.management.auth.dto.AuthenticationResponseDTO;
import io.jsonwebtoken.io.IOException;

public interface AuthenticationServiceInterface {

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    AuthenticationResponseDTO refreshToken(String refreshToken) throws IOException;
}
