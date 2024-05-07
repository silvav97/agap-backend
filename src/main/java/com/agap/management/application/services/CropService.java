package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.IProjectApplicationService;
import com.agap.management.application.ports.IProjectService;
import com.agap.management.application.ports.IUserService;
import com.agap.management.domain.dtos.request.CropRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
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
    private final IProjectService projectService;
    private final IProjectApplicationService projectApplicationService;
    private final ModelMapper modelMapper;

    @Override
    public List<CropResponseDTO> findAll() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropResponseDTO> findAll(Pageable pageable) {
        Page<Crop> page = cropRepository.findAll(pageable);
        return page.map(crop -> modelMapper.map(crop, CropResponseDTO.class));
    }

    @Override
    public Optional<CropResponseDTO> findById(Integer id) {
        Optional<Crop> optionalCrop = cropRepository.findById(id);
        return optionalCrop.map(crop -> modelMapper.map(crop, CropResponseDTO.class));
    }

    @Override
    public CropResponseDTO save(CropRequestDTO cropRequestDTO) {
        Crop crop = modelMapper.map(cropRequestDTO, Crop.class);
        crop.setProject(projectService.getProjectById(cropRequestDTO.getProjectId()));
        crop.setProjectApplication(projectApplicationService.getProjectApplicationById(
                cropRequestDTO.getProjectApplicationId()));

        Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropResponseDTO.class);
    }

    @Override
    public CropResponseDTO update(Integer id, CropRequestDTO cropRequestDTO) {
        Crop crop = getCropById(id);

        modelMapper.map(cropRequestDTO, crop);
        Crop savedCrop = cropRepository.save(crop);
        return modelMapper.map(savedCrop, CropResponseDTO.class);
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

    @Override
    public Crop getCropById(Integer id) {
        return cropRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Cultivo", "id", id.toString()));
    }

}
