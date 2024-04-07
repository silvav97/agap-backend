package com.agap.management.application.ports;

import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.User;

public interface ITokenService {

    void revokeAllUserTokens(User user);

    void saveUserToken(User user, String jwtToken, TokenType tokenType);

    void invalidateToken(Token verificationToken);

    Token verifyToken(String token);
}
