package com.agap.management.application.services;

import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.Project;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final IProjectRepository projectRepository;
    private final ModelMapper modelMapper;

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
    public Project getProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Proyecto", "id", id.toString()));
    }

}
