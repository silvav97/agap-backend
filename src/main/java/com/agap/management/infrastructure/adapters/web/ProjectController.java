package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IPesticideService;
import com.agap.management.application.ports.IProjectService;
import com.agap.management.domain.dtos.PesticideDTO;
import com.agap.management.domain.dtos.ProjectDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

}
