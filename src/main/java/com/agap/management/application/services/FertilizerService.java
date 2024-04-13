package com.agap.management.application.services;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FertilizerService implements IFertilizerService {

    private final IFertilizerRepository fertilizerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FertilizerDTO> findAll() {
        return fertilizerRepository.findAll().stream()
                .map(fertilizer -> modelMapper.map(fertilizer, FertilizerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<FertilizerDTO> findAll(Pageable pageable) {
        Page<Fertilizer> page = fertilizerRepository.findAll(pageable);
        return page.map(fertilizer -> modelMapper.map(fertilizer, FertilizerDTO.class));
    }

    @Override
    public Optional<FertilizerDTO> findById(Integer id) {
        Optional<Fertilizer> optionalFertilizer = fertilizerRepository.findById(id);
        return optionalFertilizer.map(fertilizer -> modelMapper.map(fertilizer, FertilizerDTO.class));
    }

    @Override
    public FertilizerDTO save(FertilizerDTO fertilizerDTO) {
        Fertilizer fertilizer = modelMapper.map(fertilizerDTO, Fertilizer.class);
        Fertilizer savedFertilizer = fertilizerRepository.save(fertilizer);
        return modelMapper.map(savedFertilizer, FertilizerDTO.class);
    }
    @Override
    public FertilizerDTO update(Integer id, FertilizerDTO fertilizerDTO) {
        Fertilizer fertilizer = fertilizerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Fertilizer", "id", id.toString()));

        modelMapper.map(fertilizerDTO, fertilizer);    // Actualiza fertilizer con los campos de fertilizerDTO sin perder el id u otros campos no incluidos en el DTO
        Fertilizer savedFertilizer = fertilizerRepository.save(fertilizer);
        return modelMapper.map(savedFertilizer, FertilizerDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            fertilizerRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}

