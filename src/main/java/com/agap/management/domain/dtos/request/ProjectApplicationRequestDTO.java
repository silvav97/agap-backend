package com.agap.management.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectApplicationRequestDTO {

    private Integer id;
    private Integer projectId;
    private Integer applicantId;
    private String farmName;
    private int area;
    private String municipality;
}
