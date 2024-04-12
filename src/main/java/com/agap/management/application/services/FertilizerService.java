package com.agap.management.application.services;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FertilizerService implements IFertilizerService {

    private final IFertilizerRepository fertilizerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Fertilizer> findAll() {
        return fertilizerRepository.findAll();
    }

    @Override
    public Page<Fertilizer> findAll(Pageable pageable) {
        return fertilizerRepository.findAll(pageable);
    }

    @Override
    public Optional<Fertilizer> findById(Integer id) {
        return fertilizerRepository.findById(id);
    }

    @Override
    public Fertilizer save(FertilizerDTO fertilizerdto) {
        Fertilizer fertilizer = modelMapper.map(fertilizerdto, Fertilizer.class);
        return fertilizerRepository.save(fertilizer);
    }
}
