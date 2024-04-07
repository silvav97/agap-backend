package com.agap.management.auth.definitivo;

import com.agap.management.auth.dto.AuthenticationRequestDTO;
import com.agap.management.config.JwtService;
import com.agap.management.token.TokenType;
import com.agap.management.user.Role;
import com.agap.management.user.User;
import com.agap.management.user.UserRepository;
import com.agap.management.auth.dto.AuthenticationResponseDTO;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationServiceInterface {

    private final UserRepository userRepository;
    private final ZTokenServiceInterface tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Usuario no verificado");
        }

        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.BEARER);

        return buildAuthenticationResponse(user, jwtToken, refreshToken);
    }

    @Override
    public AuthenticationResponseDTO refreshToken(String refreshToken) throws IOException {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token is missing, empty or malformed");
        }
        refreshToken = refreshToken.substring(7);

        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = this.userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if ( !jwtService.isTokenValid(refreshToken, user) ) {
            throw new RuntimeException("Invalid refresh token");
        }

        var accessToken = jwtService.generateToken(user);   // tal vez deba cambiar
        tokenService.revokeAllUserTokens(user);                          // de orden estas dos
        tokenService.saveUserToken(user, accessToken, TokenType.BEARER);

        return buildAuthenticationResponse(user, accessToken, refreshToken);
    }


    private AuthenticationResponseDTO buildAuthenticationResponse(User user, String accessToken, String refreshToken) {
        AuthenticationResponseDTO.UserResponseDTO userResponseDTO = new AuthenticationResponseDTO.UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getFirstName() + " " + user.getLastName());
        userResponseDTO.setActive(user.isEnabled());
        userResponseDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));

        return AuthenticationResponseDTO.builder()
                .user(userResponseDTO)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
