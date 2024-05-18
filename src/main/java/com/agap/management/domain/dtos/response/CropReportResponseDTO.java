package com.agap.management.domain.dtos.response;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropReportResponseDTO {

/*    @NonNull
    private Integer id;*/

    @NonNull
    private CropResponseDTO crop;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float totalSale;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float assignedBudget;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float realExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float profit;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    private float profitability;
}
