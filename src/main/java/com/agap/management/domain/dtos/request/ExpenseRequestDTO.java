package com.agap.management.domain.dtos.request;

import com.agap.management.domain.enums.ExpenseType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequestDTO {

    private Integer id;

    private Integer cropId;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float expenseValue;

    private ExpenseType expenseDescription;

    @NotNull(message = "El campo Fecha Gasto es obligatorio.")
    private LocalDate expenseDate;

}
