package com.agap.management.services;

import com.agap.management.application.ports.IEmailService;
import com.agap.management.application.ports.IReportService;
import com.agap.management.application.services.CropService;
import com.agap.management.domain.dtos.request.CropRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.domain.entities.User;
import com.agap.management.domain.enums.ApplicationStatus;
import com.agap.management.domain.enums.ProcessStatus;
import com.agap.management.infrastructure.adapters.persistence.ICropRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CropServiceTest {
    @Mock private ICropRepository cropRepository;
    @Mock private IProjectRepository projectRepository;
    @Mock private IProjectApplicationRepository projectApplicationRepository;
    @Mock private IEmailService emailService;
    @Mock private IReportService reportService;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private CropService cropService;

    private Crop crop;
    private CropRequestDTO cropRequestDTO;
    private CropResponseDTO cropResponseDTO;
    private ProjectApplication projectApplication;

    @BeforeEach
    void setUp() {
        crop = new Crop();
        crop.setId(1);
        crop.setStatus(ProcessStatus.CREADO);

        cropRequestDTO = new CropRequestDTO();
        cropRequestDTO.setProjectApplicationId(1);

        cropResponseDTO = new CropResponseDTO();
        cropResponseDTO.setId(1);

        User applicant = new User();
        applicant.setEmail("test@example.com");
        applicant.setFirstName("NombreEjemplo");

        projectApplication = new ProjectApplication();
        projectApplication.setId(1);
        projectApplication.setApplicationStatus(ApplicationStatus.PENDIENTE);
        projectApplication.setApplicant(applicant);
        projectApplication.setFarmName("Finca Ejemplo");

        Project project = new Project();
        project.setId(1);
        project.setName("Proyecto Ejemplo");
        projectApplication.setProject(project);

        crop.setProjectApplication(projectApplication);
    }

    @Test
    void testFindAll() {
        when(cropRepository.findAll()).thenReturn(Collections.singletonList(crop));
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        List<CropResponseDTO> result = cropService.findAll();
        assertEquals(1, result.size());
        assertEquals(cropResponseDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<Crop> page = new PageImpl<>(Collections.singletonList(crop));
        when(cropRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        Page<CropResponseDTO> result = cropService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(cropResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(cropRepository.findById(anyInt())).thenReturn(Optional.of(crop));
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        CropResponseDTO result = cropService.findById(1);
        assertEquals(cropResponseDTO, result);
    }

    @Test
    void testSave() throws MessagingException {
        when(modelMapper.map(any(CropRequestDTO.class), eq(Crop.class))).thenReturn(crop);
        when(projectApplicationRepository.findById(anyInt())).thenReturn(Optional.of(projectApplication));
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        CropResponseDTO result = cropService.save(cropRequestDTO);
        assertEquals(cropResponseDTO, result);
        verify(emailService, times(1)).sendEmail(
                eq("test@example.com"),
                eq("Aplicación a proyecto Aprobada"),
                eq("Hola NombreEjemplo, tu aplicación al proyecto 'Proyecto Ejemplo' fue aprobada"),
                isNull(),
                isNull()
        );
    }


    @Test
    void testUpdate() {
        when(cropRepository.findById(anyInt())).thenReturn(Optional.of(crop));

        doAnswer(invocation -> {
            invocation.getArgument(0);
            Crop entity = invocation.getArgument(1);
            entity.setProjectApplication(projectApplication);
            return null;
        }).when(modelMapper).map(any(CropRequestDTO.class), any(Crop.class));

        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        CropResponseDTO result = cropService.update(1, cropRequestDTO);
        assertEquals(cropResponseDTO, result);
    }

    @Test
    void testDelete() {
        doNothing().when(cropRepository).deleteById(anyInt());

        Boolean result = cropService.delete(1);
        assertTrue(result);
    }

    @Test
    void testFinish() {
        when(cropRepository.findById(anyInt())).thenReturn(Optional.of(crop));
        when(cropRepository.save(any(Crop.class))).thenReturn(crop);
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);
        when(cropRepository.findByProjectApplication_Project_Id(anyInt())).thenReturn(Collections.singletonList(crop));

        CropResponseDTO result = cropService.finish(1, 100.0f);
        assertEquals(cropResponseDTO, result);
        verify(reportService, times(1)).generateCropReport(any(Crop.class));
        verify(reportService, times(1)).generateProjectReport(any(Project.class));
    }

    @Test
    void testFindAllByUserId() {
        Page<Crop> page = new PageImpl<>(Collections.singletonList(crop));
        when(cropRepository.findByProjectApplication_Applicant_Id(any(Pageable.class), anyInt())).thenReturn(page);
        when(modelMapper.map(any(Crop.class), eq(CropResponseDTO.class))).thenReturn(cropResponseDTO);

        Page<CropResponseDTO> result = cropService.findAllByUserId(PageRequest.of(0, 10), 1);
        assertEquals(1, result.getContent().size());
        assertEquals(cropResponseDTO, result.getContent().get(0));
    }

    @Test
    void testGetCropById() {
        when(cropRepository.findById(anyInt())).thenReturn(Optional.of(crop));
        Crop result = cropService.getCropById(1);
        assertEquals(crop, result);
    }

}
