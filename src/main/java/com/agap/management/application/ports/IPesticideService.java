package com.agap.management.application.ports;

import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IPesticideService {

    List<Pesticide> findAll();

    Page<Pesticide> findAll(Pageable pageable);


    Optional<Pesticide> findById(Integer id);
}
