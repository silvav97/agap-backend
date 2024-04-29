package com.agap.management.application.ports;

import com.agap.management.domain.dtos.CropRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropService {
    List<CropRequestDTO> findAll();
    Page<CropRequestDTO> findAll(Pageable pageable);
    Optional<CropRequestDTO> findById(Integer id);
    CropRequestDTO save(CropRequestDTO cropRequestDTO);
    CropRequestDTO update(Integer id, CropRequestDTO cropRequestDTO);
    Boolean delete(Integer id);
}
