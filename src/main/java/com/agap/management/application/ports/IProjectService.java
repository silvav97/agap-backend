package com.agap.management.application.ports;

import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.dtos.ProjectDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProjectService {

    List<ProjectResponseDTO> findAll();
    Page<ProjectResponseDTO> findAll(Pageable pageable);
}
