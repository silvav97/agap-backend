package com.agap.management.application.ports;

import com.agap.management.domain.dtos.ProjectDTO;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropTypeService {
    List<CropTypeResponseDTO> findAll();
    Page<CropTypeResponseDTO> findAll(Pageable pageable);
    Optional<CropTypeResponseDTO> findById(Integer id);
    CropTypeResponseDTO save(CropTypeRequestDTO cropTypeRequestDTO);
    CropTypeResponseDTO update(Integer id, CropTypeRequestDTO cropTypeRequestDTO);
    Boolean delete(Integer id);

    List<String> findRelatedProjects(Integer id);
}
