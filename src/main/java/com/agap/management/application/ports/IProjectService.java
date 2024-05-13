package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.ProjectRequestDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProjectService {
    List<ProjectResponseDTO> findAll();
    Page<ProjectResponseDTO> findAll(Pageable pageable);
    ProjectResponseDTO findById(Integer id);
    ProjectResponseDTO save(ProjectRequestDTO projectRequestDTO);
    ProjectResponseDTO update(Integer id, ProjectRequestDTO projectRequestDTO);
    Boolean delete(Integer id);
    Project getProjectById(Integer id);
    List<String> findRelatedProjectApplications(Integer id);
}
