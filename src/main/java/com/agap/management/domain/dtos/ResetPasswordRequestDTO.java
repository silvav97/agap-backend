package com.agap.management.domain.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestDTO {

    @Size(min = 8, message = "Password must be at least {min} characters")
    private String newPassword;

    @Size(min = 8, message = "Password must be at least {min} characters")
    private String confirmationPassword;
}
