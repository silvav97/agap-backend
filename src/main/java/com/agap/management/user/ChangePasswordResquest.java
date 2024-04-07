package com.agap.management.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordResquest {

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
