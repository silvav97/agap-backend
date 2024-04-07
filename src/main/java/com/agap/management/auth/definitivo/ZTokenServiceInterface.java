package com.agap.management.auth.definitivo;

import com.agap.management.token.Token;
import com.agap.management.token.TokenType;
import com.agap.management.user.User;

public interface ZTokenServiceInterface {

    void revokeAllUserTokens(User user);

    void saveUserToken(User user, String jwtToken, TokenType tokenType);

    void invalidateToken(Token verificationToken);

    Token verifyToken(String token);
}
