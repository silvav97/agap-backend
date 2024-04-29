package com.agap.management.application.ports;

import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.entities.Pesticide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPesticideService {
    List<PesticideDTO> findAll();
    Page<PesticideDTO> findAll(Pageable pageable);
    PesticideDTO findById(Integer id);
    PesticideDTO save(PesticideDTO pesticideDTO);
    PesticideDTO update(Integer id, PesticideDTO pesticideDTO);
    Boolean delete(Integer id);
    Pesticide getPesticideById(Integer id);
}
