package com.agap.management.application.services.common;

import com.agap.management.domain.dtos.LoginResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthenticationUtil {

    public LoginResponseDTO buildAuthenticationResponse(String accessToken, String refreshToken) {
        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
