package com.agap.management.domain.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "En email no puede estar vacio")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos {min} caracteres")
    private String password;
}
