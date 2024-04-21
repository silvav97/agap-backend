package com.agap.management.application.ports;

import com.agap.management.domain.dtos.FertilizerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFertilizerService {
    List<FertilizerDTO> findAll();
    Page<FertilizerDTO> findAll(Pageable pageable);
    Optional<FertilizerDTO> findById(Integer id);
    FertilizerDTO save(FertilizerDTO fertilizerDTO);
    FertilizerDTO update(Integer id, FertilizerDTO fertilizerDTO);
    Boolean delete(Integer id);
}
