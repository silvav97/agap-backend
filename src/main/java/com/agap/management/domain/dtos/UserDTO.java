package com.agap.management.domain.dtos;

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
public class UserDTO {

    private Integer id;

    @NotBlank(message = "El campo Nombre es obligatorio")
    @Size(max = 50, message = "El campo Nombre debe tener máximo {max} caracteres")
    private String firstName;

    @NotBlank(message = "El campo Apellido es obligatorio")
    @Size(max = 50, message = "El campo Apellido debe tener máximo {max} caracteres")
    private String lastName;

    @NotBlank(message = "El campo Email es obligatorio")
    @Email(message = "Email should be valid")
    private String email;

}
