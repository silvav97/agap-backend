package com.agap.management.application.ports;

import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.ProjectReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportService {
    List<CropReportResponseDTO> findAllByProjectId(Integer projectId);
    Page<CropReportResponseDTO> findAllByProjectId(Pageable pageable, Integer projectId);
    CropReportResponseDTO findByCropId(Integer id);
    CropReportResponseDTO generateCropReport(Crop crop);
    ProjectReport generateProjectReport();
}
