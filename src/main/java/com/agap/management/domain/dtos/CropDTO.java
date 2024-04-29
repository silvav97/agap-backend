package com.agap.management.domain.dtos;

import com.agap.management.domain.enums.ProcessStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CropDTO {

    private Integer id;

    @NotBlank(message = "El campo Usuario ID es obligatorio")
    private String userId;

    private UserDTO user;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private Integer projectId;

    private ProjectDTO project;

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

    private List<ExpenseDTO> expenseList;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private Float saleValue;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private int area;

}
