package com.agap.management.domain.dtos.request;

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
public class CropRequestDTO {

    private Integer id;

    private Integer userId;

    private Integer projectId;

    private Integer projectApplicationId;

    @NotBlank(message = "El campo Cultivo es obligatorio")
    @Size(max = 100, message = "El campo Cultivo no puede tener más de {max} caracteres")
    private String name;

    private ProcessStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float assignedBudget;

    //private List<ExpenseResponseDTO> expenseList;

    private Float saleValue;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private int area;

}
