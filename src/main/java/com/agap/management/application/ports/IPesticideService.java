package com.agap.management.application.ports;

import com.agap.management.domain.dtos.PesticideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPesticideService {
    List<PesticideDTO> findAll();
    Page<PesticideDTO> findAll(Pageable pageable);
    Optional<PesticideDTO> findById(Integer id);
    PesticideDTO save(PesticideDTO pesticideDTO);
    PesticideDTO update(Integer id, PesticideDTO pesticideDTO);
    Boolean delete(Integer id);
}
