package com.agap.management.application.ports;

import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.ProjectReportResponseDTO;
import com.agap.management.domain.entities.Crop;
import com.agap.management.domain.entities.CropReport;
import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.ProjectReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReportService {
    List<CropReportResponseDTO> findAllCropReportsByProjectId(Integer projectId);
    Page<CropReportResponseDTO> findAllCropReportsByProjectId(Pageable pageable, Integer projectId);
    List<ProjectReportResponseDTO> findAllProjectReports();
    Page<ProjectReportResponseDTO> findAllProjectReports(Pageable pageable);
    ProjectReportResponseDTO findProjectReportById(Integer id);
    CropReportResponseDTO findCropReportByCropId(Integer id);
    CropReport generateCropReport(Crop crop);
    ProjectReport generateProjectReport(Project project);
}
