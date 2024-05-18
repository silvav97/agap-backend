package com.agap.management.domain.dtos.response;

import com.agap.management.domain.entities.Project;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReportResponseDTO {

    private Integer id;

    private ProjectResponseDTO project;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float totalSale;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float totalBudget;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float realExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float profit;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float profitability;

}
