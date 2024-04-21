package com.agap.management.domain.dtos;

import com.agap.management.domain.entities.Role;
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
public class RegisterRequestDTO {

    @NotBlank(message = "Primer nombre es requerido")
    private String firstname;

    @NotBlank(message = "Apellido es requerido")
    private String lastname;

    @Email(message = "El email debe tener un formato valido")
    @NotBlank(message = "En email no puede estar vacio")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos {min} caracteres")
    private String password;

    private Role role;
}
