package com.agap.management.services;

import com.agap.management.application.services.ReportService;
import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.ProjectReportResponseDTO;
import com.agap.management.domain.entities.*;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.ICropReportRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectReportRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private ICropReportRepository cropReportRepository;

    @Mock
    private IProjectReportRepository projectReportRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportService reportService;

    private CropReport cropReport;
    private ProjectReport projectReport;
    private CropReportResponseDTO cropReportResponseDTO;
    private ProjectReportResponseDTO projectReportResponseDTO;
    private Crop crop;
    private Project project;
    private Expense expense;

    @BeforeEach
    void setUp() {
        cropReport = CropReport.builder().id(1).build();
        projectReport = ProjectReport.builder().id(1).build();

        cropReportResponseDTO = new CropReportResponseDTO();
        cropReportResponseDTO.setId(1);

        projectReportResponseDTO = new ProjectReportResponseDTO();
        projectReportResponseDTO.setId(1);

        crop = new Crop();
        crop.setId(1);
        crop.setAssignedBudget(1000f);
        crop.setExpectedExpense(900f);
        crop.setSaleValue(1200f);

        project = new Project();
        project.setId(1);

        expense = new Expense();
        expense.setExpenseValue(800f);
    }

    @Test
    void testFindAllCropReportsByProjectId() {
        when(cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(anyInt())).thenReturn(Collections.singletonList(cropReport));
        when(modelMapper.map(any(CropReport.class), eq(CropReportResponseDTO.class))).thenReturn(cropReportResponseDTO);

        List<CropReportResponseDTO> result = reportService.findAllCropReportsByProjectId(1);
        assertEquals(1, result.size());
        assertEquals(cropReportResponseDTO, result.get(0));
    }

    @Test
    void testFindAllCropReportsByProjectIdPageable() {
        Page<CropReport> page = new PageImpl<>(Collections.singletonList(cropReport));
        when(cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(any(Pageable.class), anyInt())).thenReturn(page);
        when(modelMapper.map(any(CropReport.class), eq(CropReportResponseDTO.class))).thenReturn(cropReportResponseDTO);

        Page<CropReportResponseDTO> result = reportService.findAllCropReportsByProjectId(PageRequest.of(0, 10), 1);
        assertEquals(1, result.getContent().size());
        assertEquals(cropReportResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindCropReportByCropId() {
        when(cropReportRepository.findByCrop_Id(anyInt())).thenReturn(cropReport);
        when(modelMapper.map(any(CropReport.class), eq(CropReportResponseDTO.class))).thenReturn(cropReportResponseDTO);

        CropReportResponseDTO result = reportService.findCropReportByCropId(1);
        assertEquals(cropReportResponseDTO, result);
    }

    @Test
    void testFindCropReportByCropIdNotFound() {
        when(cropReportRepository.findByCrop_Id(anyInt())).thenReturn(null);

        assertThrows(EntityNotFoundByFieldException.class, () -> {
            reportService.findCropReportByCropId(1);
        });
    }

    @Test
    void testGenerateCropReport() {
        crop.setExpenseList(Collections.singletonList(expense));
        when(cropReportRepository.save(any(CropReport.class))).thenReturn(cropReport);

        CropReport result = reportService.generateCropReport(crop);
        assertNotNull(result);
        assertEquals(cropReport, result);
    }

    @Test
    void testFindAllProjectReports() {
        when(projectReportRepository.findAll()).thenReturn(Collections.singletonList(projectReport));
        when(modelMapper.map(any(ProjectReport.class), eq(ProjectReportResponseDTO.class))).thenReturn(projectReportResponseDTO);

        List<ProjectReportResponseDTO> result = reportService.findAllProjectReports();
        assertEquals(1, result.size());
        assertEquals(projectReportResponseDTO, result.get(0));
    }

    @Test
    void testFindAllProjectReportsPageable() {
        Page<ProjectReport> page = new PageImpl<>(Collections.singletonList(projectReport));
        when(projectReportRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(ProjectReport.class), eq(ProjectReportResponseDTO.class))).thenReturn(projectReportResponseDTO);

        Page<ProjectReportResponseDTO> result = reportService.findAllProjectReports(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(projectReportResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindProjectReportById() {
        when(projectReportRepository.findById(anyInt())).thenReturn(Optional.of(projectReport));
        when(modelMapper.map(any(ProjectReport.class), eq(ProjectReportResponseDTO.class))).thenReturn(projectReportResponseDTO);

        ProjectReportResponseDTO result = reportService.findProjectReportById(1);
        assertEquals(projectReportResponseDTO, result);
    }

    @Test
    void testFindProjectReportByIdNotFound() {
        when(projectReportRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundByFieldException.class, () -> {
            reportService.findProjectReportById(1);
        });
    }

    @Test
    void testGenerateProjectReport() {
        when(cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(anyInt())).thenReturn(Collections.singletonList(cropReport));
        when(projectReportRepository.save(any(ProjectReport.class))).thenReturn(projectReport);

        ProjectReport result = reportService.generateProjectReport(project);
        assertNotNull(result);
        assertEquals(projectReport, result);
    }
}
