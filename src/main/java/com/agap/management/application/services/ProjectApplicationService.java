package com.agap.management.application.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.IProjectApplicationService;
import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.*;
import com.agap.management.domain.enums.ApplicationStatus;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService implements IProjectApplicationService {

    private final IProjectApplicationRepository projectApplicationRepository;
    private final IProjectRepository projectRepository;
    private final IProjectService projectService;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;
    private final IEmailService emailService;


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
    public Page<ProjectApplicationResponseDTO> findAllByUserId(Pageable pageable, Integer userId) {
        Page<ProjectApplication> page = projectApplicationRepository.findByApplicantId(pageable, userId);
        return page.map(projectApplication -> modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class));
    }

    @Override
    public Page<ProjectApplicationResponseDTO> findAllByProjectId(Pageable pageable, Integer projectId) {
        Page<ProjectApplication> page = projectApplicationRepository.findByProjectId(pageable, projectId);
        return page.map(projectApplication -> modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class));
    }

    @Override
    public ProjectApplicationResponseDTO findById(Integer id) {
        ProjectApplication projectApplication = projectApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("ProjectApplication", "id", id.toString()));
        return modelMapper.map(projectApplication, ProjectApplicationResponseDTO.class);
    }

    @Override
    public ProjectApplicationResponseDTO save(ProjectApplicationRequestDTO projectApplicationRequestDTO) {

        ProjectApplication projectApplication = modelMapper.map(projectApplicationRequestDTO, ProjectApplication.class);
        Project project = projectRepository.findById(projectApplicationRequestDTO.getProjectId()).orElseThrow(() -> new EntityNotFoundByFieldException("Project", "id", projectApplicationRequestDTO.getProjectId().toString()));
        User applicant = userRepository.findById(projectApplicationRequestDTO.getApplicantId()).orElseThrow(() -> new EntityNotFoundByFieldException("User", "id", projectApplicationRequestDTO.getApplicantId().toString()));
        if (!projectApplicationRequestDTO.getMunicipality().equals(project.getMunicipality())) {
            throw new RuntimeException("Tu Municipio no coincide con el municipio del proyecto al que quieres aplicar");
        }

        projectApplication.setProject(project);
        projectApplication.setApplicant(applicant);
        projectApplication.setApplicationStatus(ApplicationStatus.PENDIENTE);
        projectApplication.setApplicationDate(LocalDate.now());

        ProjectApplication savedProjectApplication = projectApplicationRepository.save(projectApplication);
        return modelMapper.map(savedProjectApplication, ProjectApplicationResponseDTO.class);
    }

    @Override
    public ProjectApplicationResponseDTO update(Integer id, ProjectApplicationRequestDTO projectApplicationRequestDTO) {
        ProjectApplication projectApplication = projectApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Aplicación a Project", "id", id.toString()));

        modelMapper.map(projectApplicationRequestDTO, projectApplication);

        Project project = projectRepository.findById(projectApplicationRequestDTO.getProjectId()).orElseThrow(() -> new EntityNotFoundByFieldException("Project", "id", projectApplicationRequestDTO.getProjectId().toString()));

        if (!projectApplicationRequestDTO.getMunicipality().equals(project.getMunicipality())) {
            throw new RuntimeException("Tu Municipio no coincide con el municipio del proyecto al que quieres aplicar");
        }
        ProjectApplication savedProjectApplication = projectApplicationRepository.save(projectApplication);
        return modelMapper.map(savedProjectApplication, ProjectApplicationResponseDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            projectApplicationRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<String, String> reject(Integer id) throws MessagingException {
        ProjectApplication projectApplication = projectApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("ProjectApplication", "id", id.toString()));

        projectApplication.setApplicationStatus(ApplicationStatus.RECHAZADO);
        projectApplicationRepository.save(projectApplication);

        String content = String.format("Su aplicación al proyecto %s fue rechazada", projectApplication.getProject().getName());
        emailService.sendEmail(projectApplication.getApplicant().getEmail(), "Aplicación Rechazada", content, null, null);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Rejected Successfully");
        return response;
    }

    @Override
    public ProjectApplication getProjectApplicationById(Integer id) {
        return projectApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Aplicacion a proyecto", "id", id.toString()));
    }

}
