package com.agap.management.application.services;

import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.domain.dtos.FertilizerDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FertilizerService implements IFertilizerService {

    private final IFertilizerRepository fertilizerRepository;
    private final ICropTypeRepository   cropTypeRepository;
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
    public FertilizerDTO findById(Integer id) {
        return modelMapper.map(getFertilizerById(id), FertilizerDTO.class);
    }

    @Override
    public FertilizerDTO save(FertilizerDTO fertilizerDTO) {
        Fertilizer fertilizer = modelMapper.map(fertilizerDTO, Fertilizer.class);
        Fertilizer savedFertilizer = fertilizerRepository.save(fertilizer);
        return modelMapper.map(savedFertilizer, FertilizerDTO.class);
    }

    @Override
    public FertilizerDTO update(Integer id, FertilizerDTO fertilizerDTO) {
        Fertilizer fertilizer = getFertilizerById(id);

        modelMapper.map(fertilizerDTO, fertilizer);    // Actualiza fertilizer con los campos de fertilizerDTO sin perder el id u otros campos no incluidos en el DTO
        Fertilizer savedFertilizer = fertilizerRepository.save(fertilizer);
        return modelMapper.map(savedFertilizer, FertilizerDTO.class);
    }

    @Override
    public Boolean delete(Integer fertilizerId) {
        try {
            List<CropType> cropTypes = cropTypeRepository.findByFertilizer_Id(fertilizerId);
            cropTypes.forEach(cropType -> cropType.setFertilizer(null));
            cropTypeRepository.saveAll(cropTypes);
            fertilizerRepository.deleteById(fertilizerId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Fertilizer getFertilizerById(Integer id) {
        return fertilizerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Fertilizante", "id", id.toString()));
    }

    @Override
    public List<String> findRelatedCropTypes(Integer fertilizerId) {
        return cropTypeRepository.findByFertilizer_Id(fertilizerId).stream()
                .map(CropType::getName)
                .collect(Collectors.toList());
    }

}

