package com.agap.management.domain.dtos.request;

import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.User;
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
public class ProjectApplicationRequestDTO {

    private Integer id;
    private Integer projectId;
    private Integer applicantId;
    private String  farmName;
    private int     area;
    private String  municipality;  // se usa para comparar con el municipality de project en el metodo
                                   // save de ProjectApplicationService y si no coinciden lanza error.

}
