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
public class ChangePasswordRequestDTO {

    @Size(min = 8, message = "La contraseña debe tener al menos {min} caracteres")
    private String currentPassword;

    @Size(min = 8, message = "La contraseña debe tener al menos {min} caracteres")
    private String newPassword;

    @Size(min = 8, message = "La contraseña debe tener al menos {min} caracteres")
    private String confirmationPassword;
}
