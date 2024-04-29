package com.agap.management.application.ports;

import com.agap.management.domain.dtos.CropDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropService {
    List<CropDTO> findAll();
    Page<CropDTO> findAll(Pageable pageable);
    Optional<CropDTO> findById(Integer id);
    CropDTO save(CropDTO cropDTO);
    CropDTO update(Integer id, CropDTO cropDTO);
    Boolean delete(Integer id);
}
