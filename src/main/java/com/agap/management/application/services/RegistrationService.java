package com.agap.management.application.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.IRegistrationService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.Role;
import com.agap.management.exceptions.personalizedException.UserAlreadyExistException;
import com.agap.management.infrastructure.adapters.persistence.IRoleRepository;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        Role role = roleRepository.findByName(RoleType.FARMER).orElseThrow(() -> new RuntimeException("Error: Role USER is not found."));
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
    public void verifyUser(String token) throws MessagingException {
        try {
            String username = jwtService.extractUsername(token);

            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

            Token verificationToken = tokenService.verifyToken(token);

            if (user.isEnabled()) {
                throw new IllegalStateException("Account is already verified.");
            }

            user.setEnabled(true);
            userRepository.save(user);
            tokenService.invalidateToken(verificationToken);

        } catch (ExpiredJwtException e) {
            // If token already expired, send email again
            User user = userRepository.findByEmail(e.getClaims().getSubject())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            sendVerificationEmail(user);
            throw new RuntimeException("Verification link has expired. A new verification email has been sent.");
        } catch (JwtException e) {
            throw new RuntimeException("Verification failed: " + e.getMessage());
        }
    }

    private void sendVerificationEmail(User user) throws MessagingException {
        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.VERIFICATION);

        String subject = "Confirmar cuenta";
        String bodyContent = "Por favor, haz click en el botón de abajo para verificar tu cuenta.";
        String url = "http://localhost:8080/api/v1/auth/verify/" + jwtToken;
        String buttonMessage = "Verificar cuenta";
        emailService.sendEmail(user.getEmail(), subject, bodyContent, url, buttonMessage);
    }

}
