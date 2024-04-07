package com.agap.management.auth.definitivo;


import com.agap.management.token.Token;
import com.agap.management.token.TokenType;
import com.agap.management.user.User;
import com.agap.management.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ZTokenServiceImpl implements ZTokenServiceInterface {

    private final TokenRepository tokenRepository;

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

    public void invalidateToken(Token verificationToken) {
        verificationToken.setRevoked(true);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Token verifyToken(String token) {
        Optional<Token> verificationTokenOptional = tokenRepository.findByToken(token);
        if (!verificationTokenOptional.isPresent() || verificationTokenOptional.get().isExpired() || verificationTokenOptional.get().isRevoked()) {
            throw new RuntimeException("Invalid or expired verification link.");
        }
        return verificationTokenOptional.get();
    }

}
