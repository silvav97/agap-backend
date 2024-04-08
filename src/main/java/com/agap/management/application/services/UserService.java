package com.agap.management.application.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.ITokenService;
import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.ForgotPasswordDTO;
import com.agap.management.domain.dtos.ResetPasswordRequestDTO;
import com.agap.management.domain.entities.Token;
import com.agap.management.domain.entities.User;
import com.agap.management.domain.enums.TokenType;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ITokenService   tokenService;
    private final IEmailService   emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService      jwtService;

    @Override
    public void changePassword(ChangePasswordRequestDTO request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if ( !passwordEncoder.matches(request.getCurrentPassword(), user.getPassword()) ) {
            throw new IllegalStateException("Wrong password");
        }
        if ( !request.getNewPassword().equals(request.getConfirmationPassword()) ) {
            throw new IllegalStateException("Passwords are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public String forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        String jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(user, jwtToken, TokenType.VERIFICATION);

        String subject = "Resetear Password";
        String content = "Para cambiar la contraseña de click en el siguiente botón.";

        // TODO Aquí enrealidad irá una url del frontend que le apuntará a la url de abajo,
        //  el token se pasa porque el frontend lo necesita para acceder a la ruta
        //String url = "http://localhost:4200/algun-endpoint-que-reciba-esto/" + jwtToken;
        String url = "http://localhost:8080/api/v1/users/reset-password/" + jwtToken;

        String buttonMessage = "Cambiar contraseña";
        emailService.sendEmail(email, subject, content, url, buttonMessage);
        return "Please check your email to set a new password";
    }

    @Override
    public String resetPassword(String token, ResetPasswordRequestDTO request) {
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        Token verificationToken = tokenService.verifyToken(token);

        tokenService.invalidateToken(verificationToken);



        if ( !request.getNewPassword().equals(request.getConfirmationPassword()) ) {
            throw new IllegalStateException("Passwords are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "New password set successfully, login with new password";
    }
}
