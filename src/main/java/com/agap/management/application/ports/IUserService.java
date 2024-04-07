package com.agap.management.application.ports;

import com.agap.management.domain.dtos.ChangePasswordRequestDTO;
import com.agap.management.domain.dtos.ForgotPasswordDTO;

import java.security.Principal;

public interface IUserService {

    void changePassword(ChangePasswordRequestDTO request, Principal connectedUser);

    String forgotPassword(ForgotPasswordDTO request);
}
