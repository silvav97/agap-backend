package com.agap.management.application.services;

import com.agap.management.application.ports.ICropTypeService;
import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
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
public class CropTypeService implements ICropTypeService {

    private final ICropTypeRepository cropTypeRepository;
    private final IFertilizerService fertilizerService;
    private final IPesticideService pesticideService;
    private final ModelMapper modelMapper;

    @Override
    public List<CropTypeResponseDTO> findAll() {
        return cropTypeRepository.findAll().stream()
                .map(cropType -> modelMapper.map(cropType, CropTypeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropTypeResponseDTO> findAll(Pageable pageable) {
        Page<CropType> page = cropTypeRepository.findAll(pageable);
        return page.map(cropType -> modelMapper.map(cropType, CropTypeResponseDTO.class));
    }

    @Override
    public Optional<CropTypeResponseDTO> findById(Integer id) {
        Optional<CropType> optionalCropType = cropTypeRepository.findById(id);
        return optionalCropType.map(cropType -> modelMapper.map(cropType, CropTypeResponseDTO.class));
    }

    @Override
    public CropTypeResponseDTO save(CropTypeRequestDTO cropTypeRequestDTO) {
        CropType cropType = modelMapper.map(cropTypeRequestDTO, CropType.class);
        cropType.setFertilizer(fertilizerService.getFertilizerById(cropTypeRequestDTO.getFertilizerId()));
        cropType.setPesticide(pesticideService.getPesticideById(cropTypeRequestDTO.getPesticideId()));

        CropType savedCropType = cropTypeRepository.save(cropType);
        return modelMapper.map(savedCropType, CropTypeResponseDTO.class);
    }

    @Override
    public CropTypeResponseDTO update(Integer id, CropTypeRequestDTO cropTypeRequestDTO) {
        CropType cropType = cropTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Tipo de cultivo", "id", id.toString()));

        modelMapper.map(cropTypeRequestDTO, cropType);
        CropType savedCropType = cropTypeRepository.save(cropType);
        return modelMapper.map(savedCropType, CropTypeResponseDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            cropTypeRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

}
