package com.agap.management.application.ports;

import com.agap.management.domain.dtos.AuthenticationRequestDTO;
import com.agap.management.domain.dtos.AuthenticationResponseDTO;
import io.jsonwebtoken.io.IOException;

public interface IAuthenticationService {

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
    AuthenticationResponseDTO refreshToken(String refreshToken) throws IOException;
}
