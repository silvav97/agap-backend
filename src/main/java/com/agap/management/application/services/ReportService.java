package com.agap.management.application.services;

import com.agap.management.application.ports.IReportService;
import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.ProjectReportResponseDTO;
import com.agap.management.domain.entities.*;
import com.agap.management.infrastructure.adapters.persistence.ICropReportRepository;
import com.agap.management.infrastructure.adapters.persistence.IProjectReportRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final ICropReportRepository cropReportRepository;
    private final IProjectReportRepository projectReportRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CropReportResponseDTO> findAllCropReportsByProjectId(Integer projectId) {
        return cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(projectId).stream()
                .map(cropReport -> modelMapper.map(cropReport, CropReportResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropReportResponseDTO> findAllCropReportsByProjectId(Pageable pageable, Integer projectId) {
        Page<CropReport> page = cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(pageable, projectId);
        return page.map(cropReport -> modelMapper.map(cropReport, CropReportResponseDTO.class));
    }

    @Override
    public CropReportResponseDTO findCropReportByCropId(Integer cropId) {
        CropReport cropReport = cropReportRepository.findByCrop_Id(cropId);
        return modelMapper.map(cropReport, CropReportResponseDTO.class);
    }

    @Override
    public CropReport generateCropReport(Crop crop) {
        float expectedExpense = crop.getExpectedExpense();
        float realExpense = calculateTotalExpenseValue(crop.getExpenseList());
        float totalSaleValue = crop.getSaleValue();
        float profit = totalSaleValue - realExpense;

        return cropReportRepository.save(
                CropReport.builder()
                        .crop(crop)
                        .totalSale(totalSaleValue)
                        .assignedBudget(crop.getAssignedBudget())
                        .expectedExpense(expectedExpense)
                        .realExpense(realExpense)
                        .profit(profit)
                        .profitability((profit / expectedExpense) * 100)
                        .build()
        );
    }

    @Override
    public List<ProjectReportResponseDTO> findAllProjectReports() {
        return projectReportRepository.findAll().stream()
                .map(projectReport -> modelMapper.map(projectReport, ProjectReportResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProjectReportResponseDTO> findAllProjectReports(Pageable pageable) {
        Page<ProjectReport> page = projectReportRepository.findAll(pageable);
        return page.map(projectReport -> modelMapper.map(projectReport, ProjectReportResponseDTO.class));
    }

    @Override
    public ProjectReportResponseDTO findProjectReportById(Integer projectId) {
        return modelMapper.map(projectReportRepository.findById(projectId), ProjectReportResponseDTO.class);
    }

    @Override
    public ProjectReport generateProjectReport(Project project) {
        List<CropReport> cropReportList = cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(
                project.getId());

        float totalSale = 0;
        float totalBudget = 0;
        float expectedExpense = 0;
        float realExpense = 0;
        float profit = 0;

        for (CropReport cropReport : cropReportList) {
            totalSale += cropReport.getTotalSale();
            totalBudget += cropReport.getAssignedBudget();
            expectedExpense += cropReport.getExpectedExpense();
            realExpense += cropReport.getRealExpense();
            profit += cropReport.getProfit();
        }

        return projectReportRepository.save(ProjectReport.builder()
                .project(project)
                .totalSale(totalSale)
                .totalBudget(totalBudget)
                .expectedExpense(expectedExpense)
                .realExpense(realExpense)
                .profit(profit)
                .profitability(totalSale - realExpense / totalBudget)
        .build());
    }


    private float calculateTotalExpenseValue(List<Expense> expenses) {
        return (float) expenses.stream()
                .mapToDouble(Expense::getExpenseValue)
                .sum();
    }

}
