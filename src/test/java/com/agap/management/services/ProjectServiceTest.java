package com.agap.management.services;

import com.agap.management.application.services.ProjectService;
import com.agap.management.domain.dtos.request.ProjectRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.domain.enums.ProcessStatus;
import com.agap.management.infrastructure.adapters.persistence.ICropRepository;
import com.agap.management.infrastructure.adapters.persistence.ICropTypeRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private IProjectRepository projectRepository;

    @Mock
    private ICropRepository cropRepository;

    @Mock
    private IProjectApplicationRepository projectApplicationRepository;

    @Mock
    private ICropTypeRepository cropTypeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectRequestDTO projectRequestDTO;
    private ProjectResponseDTO projectResponseDTO;
    private CropType cropType;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1);
        project.setStatus(ProcessStatus.CREADO);

        cropType = new CropType();
        cropType.setId(1);

        projectRequestDTO = new ProjectRequestDTO();
        projectRequestDTO.setCropTypeId(1);

        projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(1);
    }

    @Test
    void testFindAll() {
        when(projectRepository.findAll()).thenReturn(Collections.singletonList(project));
        when(modelMapper.map(any(Project.class), eq(ProjectResponseDTO.class))).thenReturn(projectResponseDTO);

        List<ProjectResponseDTO> result = projectService.findAll();
        assertEquals(1, result.size());
        assertEquals(projectResponseDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<Project> page = new PageImpl<>(Collections.singletonList(project));
        when(projectRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Project.class), eq(ProjectResponseDTO.class))).thenReturn(projectResponseDTO);

        Page<ProjectResponseDTO> result = projectService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(projectResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(modelMapper.map(any(Project.class), eq(ProjectResponseDTO.class))).thenReturn(projectResponseDTO);

        ProjectResponseDTO result = projectService.findById(1);
        assertEquals(projectResponseDTO, result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ProjectRequestDTO.class), eq(Project.class))).thenReturn(project);
        when(cropTypeRepository.findById(anyInt())).thenReturn(Optional.of(cropType));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(modelMapper.map(any(Project.class), eq(ProjectResponseDTO.class))).thenReturn(projectResponseDTO);

        ProjectResponseDTO result = projectService.save(projectRequestDTO);
        assertEquals(projectResponseDTO, result);
    }

    @Test
    void testUpdate() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));
        when(cropTypeRepository.findById(anyInt())).thenReturn(Optional.of(cropType));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        doAnswer(invocation -> {
            Project entity = invocation.getArgument(1);
            entity.setCropType(cropType);
            return null;
        }).when(modelMapper).map(any(ProjectRequestDTO.class), any(Project.class));
        when(modelMapper.map(any(Project.class), eq(ProjectResponseDTO.class))).thenReturn(projectResponseDTO);

        ProjectResponseDTO result = projectService.update(1, projectRequestDTO);
        assertEquals(projectResponseDTO, result);
    }

    @Test
    void testDelete() {
        doNothing().when(projectRepository).deleteById(anyInt());

        Boolean result = projectService.delete(1);
        assertTrue(result);
        verify(projectRepository, times(1)).deleteById(1);
    }

    @Test
    void testFindRelatedProjectApplications() {
        ProjectApplication projectApplication = new ProjectApplication();
        projectApplication.setFarmName("Farm 1");

        when(projectApplicationRepository.findByProject_Id(anyInt())).thenReturn(Collections.singletonList(projectApplication));

        List<String> result = projectService.findRelatedProjectApplications(1);
        assertEquals(1, result.size());
        assertEquals("Farm 1", result.get(0));
    }

    @Test
    void testFindRelatedCrops() {
        Crop crop = new Crop();
        crop.setId(1);

        when(cropRepository.findByProjectApplication_Project_Id(anyInt())).thenReturn(Collections.singletonList(crop));
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(new CropResponseDTO());

        List<CropResponseDTO> result = projectService.findRelatedCrops(1);
        assertEquals(1, result.size());
    }

    @Test
    void testGetProjectById() {
        when(projectRepository.findById(anyInt())).thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(1);
        assertEquals(project, result);
    }

}
