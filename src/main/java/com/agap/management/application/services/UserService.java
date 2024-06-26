package com.agap.management.application.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.request.ResetPasswordRequestDTO;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.entities.User;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.exceptions.personalizedException.ChangePasswordException;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.exceptions.personalizedException.InvalidTokenException;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.request.ChangePasswordRequestDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    @Value("${frontend.url}")
    private String frontendUrl;

    private final IUserRepository userRepository;
    private final ITokenService tokenService;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public String changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if ( !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()) ) {
            throw new ChangePasswordException("Contraseña incorrecta");
        }
        if ( !request.getNewPassword().equals(request.getConfirmationPassword()) ) {
            throw new ChangePasswordException("Las contraseñas no coinciden");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "La contraseña ha sido cambiada exitosamente";
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundByFieldException("User", "email", email));

        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.VERIFICATION);

        String subject = "Restablecer contraseña";
        String content = "Para cambiar la contraseña haga click en el siguiente botón.";
        String url = frontendUrl + "/auth/reset-password/" + jwtToken;

        String buttonMessage = "Cambiar contraseña";
        emailService.sendEmail(email, subject, content, url, buttonMessage);
        return "Por favor revisa tu correo electronico para definir una nueva contraseña";
    }

    @Override
    public String resetPassword(String bearerToken, ResetPasswordRequestDTO request) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new InvalidTokenException("Token no present in Header");
        }
        String token = bearerToken.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Usuario", "correo", email));

        Token verificationToken = tokenService.verifyToken(token);
        tokenService.invalidateToken(verificationToken);

        if ( !request.getNewPassword().equals(request.getConfirmationPassword()) ) {
            throw new ChangePasswordException("Las contraseñas no coinciden");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "La contraseña ha sido establecida exitosamente, ahora puede logearse con la nueva contraseña";
    }
}
