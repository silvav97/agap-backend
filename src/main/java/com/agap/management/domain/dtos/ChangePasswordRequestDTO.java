package com.agap.management.domain.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {

    @Size(min = 8, message = "Password must be at least {min} characters")
    private String currentPassword;

    @Size(min = 8, message = "Password must be at least {min} characters")
    private String newPassword;

    @Size(min = 8, message = "Password must be at least {min} characters")
    private String confirmationPassword;
}
