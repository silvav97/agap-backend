package com.agap.management.application.ports;

import com.agap.management.domain.dtos.CropTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICropTypeService {
    List<CropTypeDTO> findAll();
    Page<CropTypeDTO> findAll(Pageable pageable);
    Optional<CropTypeDTO> findById(Integer id);
    CropTypeDTO save(CropTypeDTO cropTypeDTO);
    CropTypeDTO update(Integer id, CropTypeDTO cropTypeDTO);
    Boolean delete(Integer id);
}
