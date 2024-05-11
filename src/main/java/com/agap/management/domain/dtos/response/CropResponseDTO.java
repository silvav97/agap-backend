package com.agap.management.domain.dtos.response;

import com.agap.management.domain.dtos.ProjectDTO;
import com.agap.management.domain.dtos.UserDTO;
import com.agap.management.domain.enums.ProcessStatus;
import jakarta.persistence.Column;
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
public class CropResponseDTO {

    private Integer id;
    private ProjectApplicationResponseDTO projectApplication;
    private ProcessStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    private float expectedExpense;
    private float assignedBudget;
    private Float saleValue;

}
