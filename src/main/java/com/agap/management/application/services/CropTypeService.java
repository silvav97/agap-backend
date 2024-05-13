package com.agap.management.application.services;

import com.agap.management.application.ports.ICropTypeService;
import com.agap.management.application.ports.IFertilizerService;
import com.agap.management.application.ports.IPesticideService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Fertilizer;
import com.agap.management.domain.entities.Pesticide;
import com.agap.management.domain.entities.Project;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IFertilizerRepository;
import com.agap.management.infrastructure.adapters.persistence.IPesticideRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CropTypeService implements ICropTypeService {

    private final ICropTypeRepository cropTypeRepository;
    private final IProjectRepository projectRepository;
    private final IFertilizerService fertilizerService;
    private final IPesticideService pesticideService;
    private final IFertilizerRepository fertilizerRepository;
    private final IPesticideRepository pesticideRepository;
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

        // Asignar manualmente las entidades de Fertilizer y Pesticide basadas en los IDs
        if (cropTypeRequestDTO.getFertilizerId() != null) {
            Fertilizer fertilizer = fertilizerRepository.findById(cropTypeRequestDTO.getFertilizerId())
                    .orElseThrow(() -> new EntityNotFoundByFieldException(
                            "Fertilizante", "id", cropTypeRequestDTO.getFertilizerId().toString()));
            cropType.setFertilizer(fertilizer);
        }
        if (cropTypeRequestDTO.getPesticideId() != null) {
            Pesticide pesticide = pesticideRepository.findById(cropTypeRequestDTO.getPesticideId())
                    .orElseThrow(() -> new EntityNotFoundByFieldException(
                            "Pesticida", "id", cropTypeRequestDTO.getPesticideId().toString()));
            cropType.setPesticide(pesticide);
        }
        CropType savedCropType = cropTypeRepository.save(cropType);
        return modelMapper.map(savedCropType, CropTypeResponseDTO.class);
    }

    @Override
    @Transactional
    public Boolean delete(Integer cropTypeId) {
        try {
            List<Project> projects = projectRepository.findByCropType_Id(cropTypeId);
            projects.forEach(project -> project.setCropType(null));
            projectRepository.saveAll(projects);
            cropTypeRepository.deleteById(cropTypeId);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> findRelatedProjects(Integer cropTypeId) {
        return projectRepository.findByCropType_Id(cropTypeId).stream()
                .map(Project::getName)
                .collect(Collectors.toList());
    }

}
