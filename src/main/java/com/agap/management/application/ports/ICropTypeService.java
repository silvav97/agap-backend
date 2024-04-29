package com.agap.management.application.ports;

import com.agap.management.domain.dtos.CropTypeRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropTypeService {
    List<CropTypeRequestDTO> findAll();
    Page<CropTypeRequestDTO> findAll(Pageable pageable);
    Optional<CropTypeRequestDTO> findById(Integer id);
    CropTypeRequestDTO save(CropTypeRequestDTO cropTypeRequestDTO);
    CropTypeRequestDTO update(Integer id, CropTypeRequestDTO cropTypeRequestDTO);
    Boolean delete(Integer id);
}
