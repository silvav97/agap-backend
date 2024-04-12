package com.agap.management.application.services;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FertilizerService implements IFertilizerService {

    private final IFertilizerRepository fertilizerRepository;

    @Override
    public List<Fertilizer> findAll() {
        return fertilizerRepository.findAll();
    }

    @Override
    public Page<Fertilizer> findAll(Pageable pageable) {
        return fertilizerRepository.findAll(pageable);
    }
}
