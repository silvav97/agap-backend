package com.agap.management.domain.dtos.response;

import com.agap.management.domain.enums.ProcessStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseDTO {

    private Integer id;

    private CropTypeResponseDTO cropType;

    @NotBlank(message = "El campo Proyecto es obligatorio")
    @Size(max = 100, message = "El campo Proyecto no puede tener más de {max} caracteres")
    private String name;

    private ProcessStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 100, message = "El campo Municipio no puede tener más de {max} caracteres")
    private String municipality;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float totalBudget;

    private String imageUrl;
}
