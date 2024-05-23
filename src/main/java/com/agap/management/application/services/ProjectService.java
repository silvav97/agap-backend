package com.agap.management.application.services;

import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.request.ProjectRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.*;
import com.agap.management.domain.enums.ProcessStatus;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropRepository;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final IProjectRepository projectRepository;
    private final ICropRepository cropRepository;
    private final IProjectApplicationRepository projectApplicationRepository;
    private final ICropTypeRepository cropTypeRepository;
    private final ModelMapper modelMapper;

    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public List<ProjectResponseDTO> findAll() {
        return projectRepository.findAll().stream()
                .map(project -> modelMapper.map(project, ProjectResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProjectResponseDTO> findAll(Pageable pageable) {
        Page<Project> page = projectRepository.findAll(pageable);
        return page.map(project -> modelMapper.map(project, ProjectResponseDTO.class));
    }

    @Override
    public ProjectResponseDTO findById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Proyecto", "id", id.toString()));

        return modelMapper.map(project, ProjectResponseDTO.class);
    }

    @Override
    public ProjectResponseDTO save(ProjectRequestDTO projectRequestDTO) {
        Project project = modelMapper.map(projectRequestDTO, Project.class);
        CropType cropType = cropTypeRepository.findById(projectRequestDTO.getCropTypeId())
                .orElseThrow(() -> new EntityNotFoundByFieldException("Tipo de cultivo", "id", projectRequestDTO.getCropTypeId().toString()));

        project.setCropType(cropType);
        project.setStatus(ProcessStatus.CREADO);

        Project savedProject = projectRepository.save(project);
        return modelMapper.map(savedProject, ProjectResponseDTO.class);
    }

    @Override
    public ProjectResponseDTO update(Integer id, ProjectRequestDTO projectRequestDTO) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Project", "id", id.toString()));

        modelMapper.map(projectRequestDTO, project);
        if (projectRequestDTO.getCropTypeId() != null) {
            CropType cropType = cropTypeRepository.findById(projectRequestDTO.getCropTypeId())
                    .orElseThrow(() -> new EntityNotFoundByFieldException("Fertilizante", "id", projectRequestDTO.getCropTypeId().toString()));
            project.setCropType(cropType);
        }

        Project savedProject = projectRepository.save(project);
        return modelMapper.map(savedProject, ProjectResponseDTO.class);
    }

    @Override
    public Boolean delete(Integer projectId) {
        try {
            List<ProjectApplication> projectApplications = projectApplicationRepository.findByProject_Id(projectId);
            projectApplications.forEach(projectApplication -> projectApplication.setProject(null));
            projectApplicationRepository.saveAll(projectApplications);

            projectRepository.deleteById(projectId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<String> findRelatedProjectApplications(Integer projectId) {
        return projectApplicationRepository.findByProject_Id(projectId).stream()
                .map(ProjectApplication::getFarmName)
                .collect(Collectors.toList());
    }

    @Override
    public List<CropResponseDTO> findRelatedCrops(Integer projectId) {
        return cropRepository.findByProjectApplication_Project_Id(projectId).stream()
                .map(crop -> modelMapper.map(crop, CropResponseDTO .class))
                .collect(Collectors.toList());

    }

    @Override
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Proyecto", "id", id.toString()));
    }

    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                Files.createDirectories(rootLocation);
                Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
                return this.rootLocation.resolve(file.getOriginalFilename()).toString();
            } catch (IOException e) {
                throw new RuntimeException("Failed to store file", e);
            }
        }
        return null;
    }
}
