package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.request.ProjectRequestDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final IProjectService projectService;

    @GetMapping
    public List<ProjectResponseDTO> getProjects() {
        return projectService.findAll();
    }

    @GetMapping("/page")
    public Page<ProjectResponseDTO> getProjectsPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return projectService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProjectResponseDTO getProjectById(@PathVariable Integer id) {
        return projectService.findById(id);
    }

    @PostMapping()
    public ProjectResponseDTO saveProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO) {
        return projectService.save(projectRequestDTO);
    }

    @PutMapping("/{id}")
    public ProjectResponseDTO updateProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO, @PathVariable Integer id) {
        return projectService.update(id, projectRequestDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProject(@PathVariable Integer id) {
        return projectService.delete(id);
    }

    @GetMapping("/{id}/relatedProjectApplications")
    public List<String> getRelatedProjectApplicationsByProjectId(@PathVariable Integer id) {
        return projectService.findRelatedProjectApplications(id);
    }

}
