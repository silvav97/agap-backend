package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IProjectApplicationService;
import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.request.CropTypeRequestDTO;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.CropTypeResponseDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.ProjectApplication;
import com.agap.management.infrastructure.adapters.persistence.IProjectApplicationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project-application")
//@PreAuthorize("hasRole('ADMIN')")   El admin solo crea, pero el user ve
public class ProjectApplicationController {

    private final IProjectApplicationService projectApplicationService;


    @GetMapping
    public List<ProjectApplicationResponseDTO> getProjectApplications() {
        System.out.println("getProjects was called: ");
        List<ProjectApplicationResponseDTO> projectApplicationList = projectApplicationService.findAll();
        System.out.println("projectApplicationList: " + projectApplicationList);
        return projectApplicationList;
    }

    @GetMapping("/page")
    public Page<ProjectApplicationResponseDTO> getProjectApplicationsPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return projectApplicationService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProjectApplicationResponseDTO getProjectApplicationById(@PathVariable Integer id) {
        ProjectApplicationResponseDTO projectApplicationResponseDTO = projectApplicationService.findById(id);
        return projectApplicationResponseDTO;
    }

    @PostMapping()
    public ProjectApplicationResponseDTO saveProjectApplication(@RequestBody @Valid ProjectApplicationRequestDTO projectApplicationRequestDTO) {
        System.out.println("\nPROJECT_APPLICATION CONTROLLER, saveProjectApplication was called ");
        System.out.println("\nPROJECT_APPLICATION CONTROLLER, projectApplicationRequestDTO: " + projectApplicationRequestDTO);
        ProjectApplicationResponseDTO cropTypeResponseDTO = projectApplicationService.save(projectApplicationRequestDTO);
        System.out.println("\nCROP_TYPE CONTROLLER, cropTypeResponseDTO: " + cropTypeResponseDTO);
        return cropTypeResponseDTO;
    }

}
