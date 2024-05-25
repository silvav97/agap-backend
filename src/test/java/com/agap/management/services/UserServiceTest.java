package com.agap.management.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.services.JwtService;
import com.agap.management.application.services.UserService;
import com.agap.management.domain.dtos.request.ChangePasswordRequestDTO;
import com.agap.management.domain.dtos.request.ResetPasswordRequestDTO;
import com.agap.management.domain.entities.Role;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.entities.User;
import com.agap.management.domain.enums.RoleType;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.exceptions.personalizedException.ChangePasswordException;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.exceptions.personalizedException.InvalidTokenException;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private ITokenService tokenService;

    @Mock
    private IEmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private User user;
    private Principal principal;
    private Token token;

    @BeforeEach
    void setUp() {
        Role role = Role.builder()
                .id(1)
                .name(RoleType.FARMER)
                .build();

        user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("encodedPassword")
                .enabled(true)
                .roles(Collections.singletonList(role))
                .build();

        principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        token = Token.builder()
                .id(1)
                .user(user)
                .token("valid-token")
                .tokenType(TokenType.VERIFICATION)
                .revoked(false)
                .expired(false)
                .build();
    }

    @Test
    void testChangePassword_Success() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("currentPassword", "newPassword", "newPassword");

        when(passwordEncoder.matches("currentPassword", user.getPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        String result = userService.changePassword(request, principal);

        assertEquals("La contraseña ha sido cambiada exitosamente", result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testChangePassword_Failure_WrongCurrentPassword() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("wrongPassword", "newPassword", "newPassword");

        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        assertThrows(ChangePasswordException.class, () -> userService.changePassword(request, principal));
    }

    @Test
    void testChangePassword_Failure_PasswordsDoNotMatch() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("currentPassword", "newPassword", "differentNewPassword");

        when(passwordEncoder.matches("currentPassword", user.getPassword())).thenReturn(true);

        assertThrows(ChangePasswordException.class, () -> userService.changePassword(request, principal));
    }

    @Test
    void testForgotPassword_Success() throws MessagingException {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        String result = userService.forgotPassword("john.doe@example.com");

        assertEquals("Por favor revisa tu correo electronico para definir una nueva contraseña", result);
        verify(emailService, times(1)).sendEmail(eq("john.doe@example.com"), eq("Restablecer contraseña"), anyString(), anyString(), anyString());
    }

    @Test
    void testForgotPassword_Failure_UserNotFound() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByFieldException.class, () -> userService.forgotPassword("john.doe@example.com"));
    }

    @Test
    void testResetPassword_Success() {
        ResetPasswordRequestDTO request = new ResetPasswordRequestDTO("newPassword", "newPassword");

        when(jwtService.extractUsername("valid-token")).thenReturn("john.doe@example.com");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(tokenService.verifyToken("valid-token")).thenReturn(token);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        String result = userService.resetPassword("Bearer valid-token", request);

        assertEquals("La contraseña ha sido establecida exitosamente, ahora puede logearse con la nueva contraseña", result);
        verify(userRepository, times(1)).save(user);
        verify(tokenService, times(1)).invalidateToken(token);
    }

    @Test
    void testResetPassword_Failure_InvalidTokenHeader() {
        ResetPasswordRequestDTO request = new ResetPasswordRequestDTO("newPassword", "newPassword");

        assertThrows(InvalidTokenException.class, () -> userService.resetPassword(null, request));
        assertThrows(InvalidTokenException.class, () -> userService.resetPassword("InvalidBearerToken", request));
    }

    @Test
    void testResetPassword_Failure_UserNotFound() {
        ResetPasswordRequestDTO request = new ResetPasswordRequestDTO("newPassword", "newPassword");

        when(jwtService.extractUsername("valid-token")).thenReturn("john.doe@example.com");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByFieldException.class, () -> userService.resetPassword("Bearer valid-token", request));
    }

    @Test
    void testResetPassword_Failure_PasswordsDoNotMatch() {
        ResetPasswordRequestDTO request = new ResetPasswordRequestDTO("newPassword", "differentNewPassword");

        when(jwtService.extractUsername("valid-token")).thenReturn("john.doe@example.com");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        when(tokenService.verifyToken("valid-token")).thenReturn(token);

        assertThrows(ChangePasswordException.class, () -> userService.resetPassword("Bearer valid-token", request));
    }
}
