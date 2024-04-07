package com.agap.management.application.services;

import com.agap.management.application.ports.RegistrationServiceInterface;
import com.agap.management.application.ports.ZTokenServiceInterface;
import com.agap.management.domain.dtos.RegisterRequestDTO;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.infrastructure.adapters.mail.SendEmailService;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.domain.entities.Role;
import com.agap.management.infrastructure.adapters.persistence.RoleRepository;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.UserRepository;
import com.agap.management.domain.dtos.RegisterResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ZTokenServiceInterface tokenService;
    private final SendEmailService sendEmailService;
    private final PasswordEncoder        passwordEncoder;
    private final JwtService jwtService;


    @Override
    public RegisterResponseDTO register(RegisterRequestDTO request) {
        Role role = roleRepository.findByName(RoleType.FARMER).orElseThrow(() -> new RuntimeException("Error: Role USER is not found."));
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        User user = User.builder()
                .firstName(request.getFirstname()).lastName(request.getLastname())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .roles(roles).enabled(false)
                .build();

        User savedUser = userRepository.save(user);

        sendVerificationEmail(savedUser);

        return RegisterResponseDTO.builder().message("Verify your account by going to your email").build();
    }

    @Override
    public void verifyUser(String token) {
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
            User user = userRepository.findByEmail(e.getClaims().getSubject())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            sendVerificationEmail(user);
            throw new RuntimeException("Verification link has expired. A new verification email has been sent.");
        } catch (JwtException e) {
            throw new RuntimeException("Verification failed: " + e.getMessage());
        }
    }

    private void sendVerificationEmail(User user) {
        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.VERIFICATION);
        String verificationLink = "http://localhost:8080/api/v1/auth/verify/" + jwtToken;
        sendEmailService.sendEmail(user.getEmail(), "Verifica tu cuenta", "Haz click en el enlace para verificar tu cuenta: " + verificationLink);
    }

}
