package com.agap.management.domain.dtos.request;

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
    private Integer projectApplicationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private float expectedExpense;
    private float assignedBudget;
}
