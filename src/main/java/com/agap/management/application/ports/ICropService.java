package com.agap.management.application.ports;

import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.Crop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropService {
    List<CropResponseDTO> findAll();
    Page<CropResponseDTO> findAll(Pageable pageable);
    Optional<CropResponseDTO> findById(Integer id);
    CropResponseDTO save(CropResponseDTO cropResponseDTO);
    CropResponseDTO update(Integer id, CropResponseDTO cropResponseDTO);
    Boolean delete(Integer id);
    Crop getCropById(Integer id);
}
