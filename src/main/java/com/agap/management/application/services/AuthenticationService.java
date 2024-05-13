package com.agap.management.application.services;

import com.agap.management.application.ports.IAuthenticationService;
import com.agap.management.application.ports.IJwtService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.services.common.AuthenticationUtil;
import com.agap.management.domain.dtos.request.LoginRequestDTO;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.User;
import com.agap.management.exceptions.personalizedException.InvalidTokenException;
import com.agap.management.exceptions.personalizedException.UserNotEnabledYetException;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.response.LoginResponseDTO;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository       userRepository;
    private final ITokenService         tokenService;
    private final IJwtService           jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new EntityNotFoundByFieldException("User", "email", email));

        if (!user.isEnabled()) {
            throw new UserNotEnabledYetException(email);
        }
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.BEARER);

        return AuthenticationUtil.buildAuthenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) throws IOException {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new InvalidTokenException("Refresh token no esta presente, ha sido perdido o malformado");
        }
        refreshToken = refreshToken.substring(7);

        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new EntityNotFoundByFieldException("Usuario", "token", "****");
        }
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(()
                -> new EntityNotFoundByFieldException("Usuario", "correo", userEmail));

        if ( !jwtService.isTokenValid(refreshToken, user) ) {
            throw new InvalidTokenException("Refresh token invalido");
        }

        var accessToken = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, accessToken, TokenType.BEARER);

        return AuthenticationUtil.buildAuthenticationResponse(accessToken, refreshToken);
    }

}
