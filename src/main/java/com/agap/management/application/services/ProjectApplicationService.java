package com.agap.management.application.services;

import com.agap.management.application.ports.IProjectApplicationService;
import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.*;
import com.agap.management.domain.enums.ApplicationStatus;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService implements IProjectApplicationService {

    private final IProjectApplicationRepository projectApplicationRepository;
    private final IProjectRepository projectRepository;
    private final IProjectService projectService;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<ProjectApplicationResponseDTO> findAll() {
        return projectApplicationRepository.findAll().stream()
                .map(projectApplication -> modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProjectApplicationResponseDTO> findAll(Pageable pageable) {
        Page<ProjectApplication> page = projectApplicationRepository.findAll(pageable);
        return page.map(projectApplication -> modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class));
    }

    @Override
    public ProjectApplicationResponseDTO findById(Integer id) {
        ProjectApplication projectApplication = projectApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("ProjectApplication", "id", id.toString()));

        ProjectApplicationResponseDTO projectApplicationResponseDTO = modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class);
        return projectApplicationResponseDTO;
    }

    @Override
    public ProjectApplicationResponseDTO save(ProjectApplicationRequestDTO projectApplicationRequestDTO) {
        System.out.println("\nPROJECT_APPLICATION SERVICE, save was called ");
        System.out.println("\nPROJECT_APPLICATION SERVICE, projectApplicationRequestDTO: " + projectApplicationRequestDTO);
        ProjectApplication projectApplication = modelMapper.map(projectApplicationRequestDTO, ProjectApplication.class);
        System.out.println("\nPROJECT_APPLICATION SERVICE, projectApplication despues del primer mapeo: " + projectApplication);


        Project project = projectRepository.findById(projectApplicationRequestDTO.getProjectId())
                .orElseThrow(() -> new EntityNotFoundByFieldException("Project", "id", projectApplicationRequestDTO.getProjectId().toString()));
        System.out.println("\nPROJECT_APPLICATION SERVICE, project id: " + project.getId());

        User applicant = userRepository.findById(projectApplicationRequestDTO.getApplicantId())
                .orElseThrow(() -> new EntityNotFoundByFieldException("User", "id", projectApplicationRequestDTO.getApplicantId().toString()));
        System.out.println("\nPROJECT_APPLICATION SERVICE, applicant id: " + applicant.getId());

        projectApplication.setProject(project);
        System.out.println("\nPROJECT_APPLICATION SERVICE, seteé Project ");

        projectApplication.setApplicant(applicant);
        System.out.println("\nPROJECT_APPLICATION SERVICE, seteé Applicant ");

        projectApplication.setApplicationStatus(ApplicationStatus.PENDING);
        System.out.println("\nPROJECT_APPLICATION SERVICE, seteé ApplicationStatus.PENDING ");
        projectApplication.setApplicationDate(LocalDate.now());
        System.out.println("\nPROJECT_APPLICATION SERVICE, seteé ApplicationDate ");

        //System.out.println("\nPROJECT_APPLICATION SERVICE, projectApplication: " + projectApplication);


        ProjectApplication savedProjectApplication = projectApplicationRepository.save(projectApplication);
        //System.out.println("\nPROJECT_APPLICATION SERVICE, savedProjectApplication: " + savedProjectApplication);
        ProjectApplicationResponseDTO projectApplicationResponseDTO = modelMapper.map(savedProjectApplication, ProjectApplicationResponseDTO.class);
        //System.out.println("\nPROJECT_APPLICATION SERVICE, projectApplicationResponseDTO: " + projectApplicationResponseDTO);
        System.out.println("\nPROJECT_APPLICATION SERVICE finished?: ");

        return projectApplicationResponseDTO;
    }


}
