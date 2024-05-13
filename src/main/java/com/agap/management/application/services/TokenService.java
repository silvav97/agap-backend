package com.agap.management.application.services;

import com.agap.management.application.ports.ITokenService;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.User;
import com.agap.management.exceptions.personalizedException.InvalidTokenException;
import com.agap.management.infrastructure.adapters.persistence.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService {

    private final ITokenRepository tokenRepository;

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void saveUserToken(User user, String jwtToken, TokenType tokenType) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(tokenType)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void invalidateToken(Token verificationToken) {
        verificationToken.setRevoked(true);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Token verifyToken(String token) {
        Optional<Token> verificationTokenOptional = tokenRepository.findByToken(token);
        if (verificationTokenOptional.isEmpty()
                || verificationTokenOptional.get().isExpired()
                || verificationTokenOptional.get().isRevoked()) {
            throw new InvalidTokenException("Token de verificación invalido o expirado");
        }
        return verificationTokenOptional.get();
    }

}
