package com.agap.management.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.services.JwtService;
import com.agap.management.application.services.RegistrationService;
import com.agap.management.domain.dtos.request.RegisterRequestDTO;
import com.agap.management.domain.dtos.response.LoginResponseDTO;
import com.agap.management.domain.dtos.response.RegisterResponseDTO;
import com.agap.management.domain.entities.Role;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.entities.User;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.exceptions.personalizedException.UserAlreadyExistException;
import com.agap.management.exceptions.personalizedException.UserAlreadyVerifiedException;
import com.agap.management.exceptions.personalizedException.VerificationTokenAlreadyExpiredException;
import com.agap.management.infrastructure.adapters.persistence.IRoleRepository;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private ITokenService tokenService;

    @Mock
    private IEmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private RegistrationService registrationService;

    private User user;
    private Role role;
    private RegisterRequestDTO registerRequestDTO;
    private Token token;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(false)
                .build();

        role = new Role();
        role.setId(1);
        role.setName(RoleType.FARMER);

        registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setEmail("john.doe@example.com");
        registerRequestDTO.setFirstname("John");
        registerRequestDTO.setLastname("Doe");
        registerRequestDTO.setPassword("password");

        token = new Token();
        token.setToken("test-token");
        token.setUser(user);
        token.setTokenType(TokenType.VERIFICATION);
    }

    @Test
    void testRegister() throws MessagingException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByName(any(RoleType.class))).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterResponseDTO result = registrationService.register(registerRequestDTO);
        assertEquals("Dirigete a tu email para verificar tu cuenta", result.getMessage());
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testRegisterUserAlreadyExists() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistException.class, () -> {
            registrationService.register(registerRequestDTO);
        });
    }

    @Test
    void testVerifyUser() throws MessagingException {
        when(jwtService.extractUsername(anyString())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.verifyToken(anyString())).thenReturn(token);
        when(jwtService.generateToken(any(User.class))).thenReturn("new-access-token");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("new-refresh-token");

        LoginResponseDTO result = registrationService.verifyUser("test-token");
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
        verify(tokenService, times(1)).invalidateToken(any(Token.class));
        verify(tokenService, times(1)).revokeAllUserTokens(any(User.class));
        verify(tokenService, times(1)).saveUserToken(any(User.class), anyString(), any(TokenType.class));
    }

    @Test
    void testVerifyUserAlreadyVerified() {
        user.setEnabled(true);
        when(jwtService.extractUsername(anyString())).thenReturn(user.getEmail());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.verifyToken(anyString())).thenReturn(token);

        assertThrows(UserAlreadyVerifiedException.class, () -> {
            registrationService.verifyUser("test-token");
        });
    }


    @Test
    void testVerifyUserExpiredToken() throws MessagingException {
        DefaultHeader header = new DefaultHeader();
        DefaultClaims claims = new DefaultClaims();
        claims.setSubject(user.getEmail());
        ExpiredJwtException expiredJwtException = new ExpiredJwtException(header, claims, "expired-token");

        when(jwtService.extractUsername(anyString())).thenThrow(expiredJwtException);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(VerificationTokenAlreadyExpiredException.class, () -> {
            registrationService.verifyUser("test-token");
        });
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testVerifyUserInvalidToken() {
        when(jwtService.extractUsername(anyString())).thenThrow(new JwtException("invalid-token"));

        assertThrows(RuntimeException.class, () -> {
            registrationService.verifyUser("test-token");
        });
    }
}
