package com.agap.management.application.ports;

import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.Fertilizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IFertilizerService {

    List<Fertilizer> findAll();

    Page<Fertilizer> findAll(Pageable pageable);

    Optional<Fertilizer> findById(Integer id);

    Fertilizer save(FertilizerDTO fertilizerdto);

}
