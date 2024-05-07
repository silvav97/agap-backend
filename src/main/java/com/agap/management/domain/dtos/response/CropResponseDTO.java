package com.agap.management.domain.dtos.response;

import com.agap.management.domain.dtos.ProjectDTO;
import com.agap.management.domain.dtos.UserDTO;
import com.agap.management.domain.entities.Expense;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.domain.enums.ProcessStatus;
import jakarta.validation.constraints.DecimalMin;
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
public class CropResponseDTO {

    private Integer id;

    private UserDTO user;

    private ProjectDTO project;

    private ProjectApplication projectApplication;

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

    private List<ExpenseResponseDTO> expenseList;

    private Float saleValue;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private int area;

}
