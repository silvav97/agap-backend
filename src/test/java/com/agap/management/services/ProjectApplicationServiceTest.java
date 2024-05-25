package com.agap.management.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.services.ProjectApplicationService;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.domain.entities.User;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import com.agap.management.infrastructure.adapters.persistence.IUserRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectApplicationServiceTest {

    @Mock
    private IProjectApplicationRepository projectApplicationRepository;
    @Mock
    private IProjectRepository projectRepository;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private IEmailService emailService;
    @InjectMocks
    private ProjectApplicationService projectApplicationService;

    private ProjectApplication projectApplication;
    private ProjectApplicationRequestDTO projectApplicationRequestDTO;
    private ProjectApplicationResponseDTO projectApplicationResponseDTO;
    private Project project;
    private User applicant;

    @BeforeEach
    void setUp() {
        projectApplication = new ProjectApplication();
        projectApplication.setId(1);

        projectApplicationRequestDTO = new ProjectApplicationRequestDTO();
        projectApplicationRequestDTO.setProjectId(1);
        projectApplicationRequestDTO.setApplicantId(1);
        projectApplicationRequestDTO.setMunicipality("SampleMunicipality");

        projectApplicationResponseDTO = new ProjectApplicationResponseDTO();
        projectApplicationResponseDTO.setId(1);

        project = new Project();
        project.setId(1);
        project.setMunicipality("SampleMunicipality");

        applicant = new User();
        applicant.setId(1);
        applicant.setEmail("test@example.com");
    }

    @Test
    void testFindAll() {
        when(projectApplicationRepository.findAll()).thenReturn(Collections.singletonList(projectApplication));
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        List<ProjectApplicationResponseDTO> result = projectApplicationService.findAll();
        assertEquals(1, result.size());
        assertEquals(projectApplicationResponseDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<ProjectApplication> page = new PageImpl<>(Collections.singletonList(projectApplication));
        when(projectApplicationRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        Page<ProjectApplicationResponseDTO> result = projectApplicationService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(projectApplicationResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindAllByUserId() {
        Page<ProjectApplication> page = new PageImpl<>(Collections.singletonList(projectApplication));
        when(projectApplicationRepository.findByApplicantId(any(Pageable.class), anyInt())).thenReturn(page);
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        Page<ProjectApplicationResponseDTO> result = projectApplicationService.findAllByUserId(PageRequest.of(0, 10), 1);
        assertEquals(1, result.getContent().size());
        assertEquals(projectApplicationResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindAllByProjectId() {
        Page<ProjectApplication> page = new PageImpl<>(Collections.singletonList(projectApplication));
        when(projectApplicationRepository.findByProjectId(any(Pageable.class), anyInt())).thenReturn(page);
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        Page<ProjectApplicationResponseDTO> result = projectApplicationService.findAllByProjectId(PageRequest.of(0, 10), 1);
        assertEquals(1, result.getContent().size());
        assertEquals(projectApplicationResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(projectApplicationRepository.findById(anyInt())).thenReturn(Optional.of(projectApplication));
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        ProjectApplicationResponseDTO result = projectApplicationService.findById(1);
        assertEquals(projectApplicationResponseDTO, result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ProjectApplicationRequestDTO.class), eq(ProjectApplication.class))).thenReturn(projectApplication);
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(applicant));
        when(projectApplicationRepository.save(any(ProjectApplication.class))).thenReturn(projectApplication);
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        ProjectApplicationResponseDTO result = projectApplicationService.save(projectApplicationRequestDTO);
        assertEquals(projectApplicationResponseDTO, result);
    }

    @Test
    void testUpdate() {
        when(projectApplicationRepository.findById(anyInt())).thenReturn(Optional.of(projectApplication));
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(projectApplicationRepository.save(any(ProjectApplication.class))).thenReturn(projectApplication);
        doAnswer(invocation -> {
            ProjectApplicationRequestDTO request = invocation.getArgument(0);
            ProjectApplication entity = invocation.getArgument(1);
            entity.setProject(project);
            entity.setApplicant(applicant);
            return null;
        }).when(modelMapper).map(any(ProjectApplicationRequestDTO.class), any(ProjectApplication.class));
        when(modelMapper.map(any(ProjectApplication.class), eq(ProjectApplicationResponseDTO.class))).thenReturn(projectApplicationResponseDTO);

        ProjectApplicationResponseDTO result = projectApplicationService.update(1, projectApplicationRequestDTO);
        assertEquals(projectApplicationResponseDTO, result);
    }


    @Test
    void testDelete() {
        doNothing().when(projectApplicationRepository).deleteById(anyInt());

        Boolean result = projectApplicationService.delete(1);
        assertTrue(result);
        verify(projectApplicationRepository, times(1)).deleteById(1);
    }

    @Test
    void testReject() throws MessagingException {
        projectApplication.setProject(project);
        projectApplication.setApplicant(applicant);

        when(projectApplicationRepository.findById(anyInt())).thenReturn(Optional.of(projectApplication));
        when(projectApplicationRepository.save(any(ProjectApplication.class))).thenReturn(projectApplication);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString(), any(), any());

        Map<String, String> result = projectApplicationService.reject(1);
        assertEquals("Rechazado exitosamente", result.get("message"));
        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString(), any(), any());
    }

    @Test
    void testGetProjectApplicationById() {
        when(projectApplicationRepository.findById(anyInt())).thenReturn(Optional.of(projectApplication));

        ProjectApplication result = projectApplicationService.getProjectApplicationById(1);
        assertEquals(projectApplication, result);
    }
}
