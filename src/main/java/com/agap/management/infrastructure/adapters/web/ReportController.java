package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IReportService;
import com.agap.management.domain.dtos.response.CropReportResponseDTO;
import com.agap.management.domain.dtos.response.ProjectReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final IReportService reportService;

    @GetMapping("/crop/byProject/{id}")
    public List<CropReportResponseDTO> findAllCropReportsByProjectId(@PathVariable Integer id) {
        return reportService.findAllCropReportsByProjectId(id);
    }

    @GetMapping("/crop/byProject/page/{id}")
    public Page<CropReportResponseDTO> findAllCropReportsByProjectIdPage(@RequestParam Integer pageNumber,
                                                                     @RequestParam Integer pageSize,
                                                                     @PathVariable Integer id) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reportService.findAllCropReportsByProjectId(pageable, id);
    }

    @GetMapping("/crop/{id}")
    public CropReportResponseDTO findCropReportByCropId(@PathVariable Integer id) {
        return reportService.findCropReportByCropId(id);
    }

    @GetMapping("/project")
    public List<ProjectReportResponseDTO> findAllProjectReports() {
        return reportService.findAllProjectReports();
    }

    @GetMapping("/project/page")
    public Page<ProjectReportResponseDTO> findAllProjectReportsPage(@RequestParam Integer pageNumber,
                                                                @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return reportService.findAllProjectReports(pageable);
    }

    @GetMapping("/project/{id}")
    public ProjectReportResponseDTO findProjectReportById(@PathVariable Integer id) {
        return reportService.findProjectReportById(id);
    }

}
