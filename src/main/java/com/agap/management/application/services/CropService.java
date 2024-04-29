package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.domain.dtos.CropDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropRepository;
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
public class CropService implements ICropService {

    private final ICropRepository cropRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CropDTO> findAll() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropDTO> findAll(Pageable pageable) {
        Page<Crop> page = cropRepository.findAll(pageable);
        return page.map(crop -> modelMapper.map(crop, CropDTO.class));
    }

    @Override
    public Optional<CropDTO> findById(Integer id) {
        Optional<Crop> optionalCrop = cropRepository.findById(id);
        return optionalCrop.map(crop -> modelMapper.map(crop, CropDTO.class));
    }

    @Override
    public CropDTO save(CropDTO cropDTO) {
        Crop crop = modelMapper.map(cropDTO, Crop.class);
        Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropDTO.class);
    }

    @Override
    public CropDTO update(Integer id, CropDTO cropDTO) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));

        modelMapper.map(cropDTO, crop);
        Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            cropRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}
