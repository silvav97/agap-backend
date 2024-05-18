package com.agap.management.application.services;

import com.agap.management.application.ports.IReportService;
import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.CropReport;
import com.agap.management.domain.entities.Expense;
import com.agap.management.domain.entities.ProjectReport;
import com.agap.management.infrastructure.adapters.persistence.ICropReportRepository;
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
    private final ModelMapper modelMapper;

    @Override
    public List<CropReportResponseDTO> findAllByProjectId(Integer projectId) {
        return cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(projectId).stream()
                .map(cropReport -> modelMapper.map(cropReport, CropReportResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<CropReportResponseDTO> findAllByProjectId(Pageable pageable, Integer projectId) {
        Page<CropReport> page = cropReportRepository.findAllByCrop_ProjectApplication_Project_Id(pageable, projectId);
        return page.map(cropReport -> modelMapper.map(cropReport, CropReportResponseDTO.class));
    }

    @Override
    public CropReportResponseDTO findByCropId(Integer cropId) {
        CropReport cropReport = cropReportRepository.findByCrop_Id(cropId);
        return modelMapper.map(cropReport, CropReportResponseDTO.class);
    }

    @Override
    public CropReportResponseDTO generateCropReport(Crop crop) {
        float expectedExpense = crop.getExpectedExpense();
        float realExpense = calculateTotalExpenseValue(crop.getExpenseList());
        float totalSaleValue = crop.getSaleValue();
        float profit = totalSaleValue - realExpense;

        CropReport generatedCropReport = cropReportRepository.save(
                CropReport.builder()
                        .crop(crop)
                        .totalSale(totalSaleValue)
                        .assignedBudget(crop.getAssignedBudget())
                        .expectedExpense(expectedExpense)
                        .realExpense(realExpense)
                        .profit(profit)
                        .profitability((profit/expectedExpense)*100)
                        .build()
        );

        return modelMapper.map(generatedCropReport, CropReportResponseDTO.class);
    }

    @Override
    public ProjectReport generateProjectReport() {
        return null;
    }

    private float calculateTotalExpenseValue(List<Expense> expenses) {
        return (float) expenses.stream()
                .mapToDouble(Expense::getExpenseValue)
                .sum();
    }

}
