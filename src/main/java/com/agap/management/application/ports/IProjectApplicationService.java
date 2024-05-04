package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProjectApplicationService {


    List<ProjectApplicationResponseDTO> findAll();
    Page<ProjectApplicationResponseDTO> findAll(Pageable pageable);

    ProjectApplicationResponseDTO findById(Integer id);

    ProjectApplicationResponseDTO save(ProjectApplicationRequestDTO projectApplicationRequestDTO);


}
