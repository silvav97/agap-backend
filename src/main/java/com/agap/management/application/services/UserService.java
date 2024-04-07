package com.agap.management.application.services;

import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.ForgotPasswordDTO;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;

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
    public String forgotPassword(ForgotPasswordDTO request) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return null;
    }
}
