package com.agap.management.domain.dtos.response;

import com.agap.management.domain.dtos.UserDTO;
import com.agap.management.domain.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectApplicationResponseDTO {

    private Integer id;
    private ProjectResponseDTO project;
    private UserDTO applicant;

    // CropDTO ?

    private ApplicationStatus applicationStatus;
    private LocalDate applicationDate;
    private LocalDate reviewDate;
    private String farmName;
    private int area;
    private String municipality;
    private String weather;

}
