package com.agap.management.application.ports;

import com.agap.management.domain.dtos.LoginRequestDTO;
import com.agap.management.domain.dtos.LoginResponseDTO;
import io.jsonwebtoken.io.IOException;

public interface IAuthenticationService {
    LoginResponseDTO login(LoginRequestDTO request);
    LoginResponseDTO refreshToken(String refreshToken) throws IOException;
}
