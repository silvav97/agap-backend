package com.agap.management.application.services;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PesticideService implements IPesticideService {

    private final IPesticideRepository pesticideRepository;

    @Override
    public List<Pesticide> findAll() {
        return pesticideRepository.findAll();
    }

    @Override
    public Page<Pesticide> findAll(Pageable pageable) {
        return pesticideRepository.findAll(pageable);
    }

    @Override
    public Optional<Pesticide> findById(Integer id) {
        return pesticideRepository.findById(id);
    }
}
