package com.agap.management.application.ports;

import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.Fertilizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFertilizerService {
    List<FertilizerDTO> findAll();
    Page<FertilizerDTO> findAll(Pageable pageable);
    FertilizerDTO findById(Integer id);
    FertilizerDTO save(FertilizerDTO fertilizerDTO);
    FertilizerDTO update(Integer id, FertilizerDTO fertilizerDTO);
    Boolean delete(Integer id);
    Fertilizer getFertilizerById(Integer id);
    List<String> findRelatedCropTypes(Integer fertilizerId);

}
