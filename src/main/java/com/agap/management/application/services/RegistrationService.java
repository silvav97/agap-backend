package com.agap.management.application.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.IRegistrationService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.services.common.AuthenticationUtil;
import com.agap.management.domain.dtos.LoginResponseDTO;
import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.Role;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.exceptions.personalizedException.UserAlreadyExistException;
import com.agap.management.exceptions.personalizedException.UserAlreadyVerifiedException;
import com.agap.management.exceptions.personalizedException.VerificationTokenAlreadyExpiredException;
import com.agap.management.infrastructure.adapters.persistence.IRoleRepository;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService implements IRegistrationService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ITokenService   tokenService;
    private final IEmailService   emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService      jwtService;

    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) throws MessagingException {
        String email = request.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) throw new UserAlreadyExistException(email);

        Role role = roleRepository.findByName(RoleType.FARMER).orElseThrow(() -> new EntityNotFoundByFieldException("Role", "name", RoleType.FARMER.name()));
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .firstName(request.getFirstname()).lastName(request.getLastname())
                .email(email).password(passwordEncoder.encode(request.getPassword()))
                .roles(roles).enabled(false)
                .build();

        User savedUser = userRepository.save(user);

        sendVerificationEmail(savedUser);

        return RegisterResponseDTO.builder().message("Verify your account by going to your email").build();
    }

    @Override
    public LoginResponseDTO verifyUser(String token) throws MessagingException {
        try {
            String email = jwtService.extractUsername(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundByFieldException("User", "email", email));

            Token verificationToken = tokenService.verifyToken(token);

            if (user.isEnabled()) {
                throw new UserAlreadyVerifiedException(user.getEmail());
            }

            user.setEnabled(true);
            userRepository.save(user);
            tokenService.invalidateToken(verificationToken);

            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            tokenService.revokeAllUserTokens(user);
            tokenService.saveUserToken(user, accessToken, TokenType.BEARER);
            return AuthenticationUtil.buildAuthenticationResponse(accessToken, refreshToken);

        } catch (ExpiredJwtException e) {
            // If token already expired, send email again
            String email = e.getClaims().getSubject();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundByFieldException("User", "email", email));

            sendVerificationEmail(user);
            throw new VerificationTokenAlreadyExpiredException(email);
        } catch (JwtException e) {
            throw new RuntimeException("Verification failed: " + e.getMessage());
        }
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.VERIFICATION);

        String subject = "Confirmar cuenta";
        String bodyContent = "Por favor, haz click en el botón de abajo para verificar tu cuenta.";
        String url = "http://localhost:4200/auth/verify/" + jwtToken;
        String buttonMessage = "Verificar cuenta";
        emailService.sendEmail(user.getEmail(), subject, bodyContent, url, buttonMessage);
    }

}
