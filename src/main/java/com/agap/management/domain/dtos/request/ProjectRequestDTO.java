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
public class ProjectRequestDTO {
    private Integer id;
    private Integer cropTypeId;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String municipality;
    private float totalBudget;
    private String imageUrl;
}
