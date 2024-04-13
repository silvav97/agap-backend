package com.agap.management.application.services;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
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
public class PesticideService implements IPesticideService {

    private final IPesticideRepository pesticideRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PesticideDTO> findAll() {
        return pesticideRepository.findAll().stream()
                .map(pesticide -> modelMapper.map(pesticide, PesticideDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<PesticideDTO> findAll(Pageable pageable) {
        Page<Pesticide> page = pesticideRepository.findAll(pageable);
        return page.map(pesticide -> modelMapper.map(pesticide, PesticideDTO.class));
    }

    @Override
    public Optional<PesticideDTO> findById(Integer id) {
        Optional<Pesticide> optionalPesticide = pesticideRepository.findById(id);
        return optionalPesticide.map(pesticide -> modelMapper.map(pesticide, PesticideDTO.class));
    }

    @Override
    public PesticideDTO save(PesticideDTO pesticideDTO) {
        Pesticide pesticide = modelMapper.map(pesticideDTO, Pesticide.class);
        Pesticide savedPesticide = pesticideRepository.save(pesticide);
        return modelMapper.map(savedPesticide, PesticideDTO.class);
    }
}
