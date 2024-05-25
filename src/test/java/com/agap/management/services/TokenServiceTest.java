package com.agap.management.services;

import com.agap.management.application.services.TokenService;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.User;
import com.agap.management.exceptions.personalizedException.InvalidTokenException;
import com.agap.management.infrastructure.adapters.persistence.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private ITokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    private User user;
    private Token token;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .enabled(true)
                .build();

        token = Token.builder()
                .id(1)
                .user(user)
                .token("valid-token")
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
    }

    @Test
    void testRevokeAllUserTokens() {
        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(List.of(token));

        tokenService.revokeAllUserTokens(user);

        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
        verify(tokenRepository, times(1)).saveAll(any());
    }

    @Test
    void testSaveUserToken() {
        tokenService.saveUserToken(user, "new-token", TokenType.BEARER);

        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testInvalidateToken() {
        tokenService.invalidateToken(token);

        assertTrue(token.isRevoked());
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void testVerifyToken_Success() {
        when(tokenRepository.findByToken("valid-token")).thenReturn(Optional.of(token));

        Token result = tokenService.verifyToken("valid-token");

        assertNotNull(result);
        assertEquals("valid-token", result.getToken());
    }

    @Test
    void testVerifyToken_Failure_InvalidToken() {
        when(tokenRepository.findByToken("invalid-token")).thenReturn(Optional.empty());

        assertThrows(InvalidTokenException.class, () -> tokenService.verifyToken("invalid-token"));
    }

    @Test
    void testVerifyToken_Failure_ExpiredToken() {
        token.setExpired(true);
        when(tokenRepository.findByToken("expired-token")).thenReturn(Optional.of(token));

        assertThrows(InvalidTokenException.class, () -> tokenService.verifyToken("expired-token"));
    }

    @Test
    void testVerifyToken_Failure_RevokedToken() {
        token.setRevoked(true);
        when(tokenRepository.findByToken("revoked-token")).thenReturn(Optional.of(token));

        assertThrows(InvalidTokenException.class, () -> tokenService.verifyToken("revoked-token"));
    }
}
