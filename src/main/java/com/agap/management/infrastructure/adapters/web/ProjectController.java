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
//@PreAuthorize("hasRole('ADMIN')")   El admin solo crea, pero el user ve
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
        //System.out.println("PROJECT_CONTROLLER. getProjectById was called: ");
        ProjectResponseDTO projectResponseDTO = projectService.findById(id);
        //System.out.println("projectResponseDTO: " + projectResponseDTO);
        return projectResponseDTO;
    }

    /*
    @PostMapping("/projects/{projectId}/image")
    public ResponseEntity<?> uploadProjectImage(@PathVariable Integer projectId, @RequestParam("image") MultipartFile file) {
        String imageUrl = imageStorageService.uploadImage(file);  // Lógica de carga de imágenes
        Project project = projectService.findById(projectId);
        project.setImageUrl(imageUrl);
        projectService.save(project);
        return ResponseEntity.ok(new MessageResponse("Imagen cargada exitosamente!"));
    }
    */

    @PostMapping()
    public ProjectResponseDTO saveProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO) {
        System.out.println("\nPROJECT CONTROLLER, saveProject was called ");
        ProjectResponseDTO projectResponseDTO = projectService.save(projectRequestDTO);
        System.out.println("\nPROJECT CONTROLLER, projectResponseDTO: " + projectResponseDTO);
        return projectResponseDTO;
    }

    @PutMapping("/{id}")
    public ProjectResponseDTO updateProject(@RequestBody @Valid ProjectRequestDTO projectRequestDTO, @PathVariable Integer id) {
        System.out.println("updateProject was called: " + id);
        ProjectResponseDTO projectResponseDTO = projectService.update(id, projectRequestDTO);
        return projectResponseDTO;
    }

    @DeleteMapping("/{id}")
    public boolean deleteProject(@PathVariable Integer id) {
        System.out.println("deleteProject was called: " + id);
        return projectService.delete(id);
    }

    @GetMapping("/{id}/relatedProjectApplications")
    public List<String> getRelatedProjectApplicationsByProjectId(@PathVariable Integer id) {
        System.out.println("getRelatedProjectApplicationsByProjectId was called: " + id);
        List<String> relatedProjectApplications = projectService.findRelatedProjectApplications(id);
        System.out.println("Related ProjectApplications: " + relatedProjectApplications);
        return relatedProjectApplications;
    }



}
