package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IProjectApplicationService;
import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.User;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project-application")
public class ProjectApplicationController {

    private final IProjectApplicationService projectApplicationService;

    @GetMapping
    public List<ProjectApplicationResponseDTO> getProjectApplications() {
        return projectApplicationService.findAll();
    }

    // Endpoint para administradores
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ProjectApplicationResponseDTO> getAdminProjectApplications(
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam(required = false) Integer projectId) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProjectApplicationResponseDTO> projectApplicationResponseDTO;

        if (projectId != null) projectApplicationResponseDTO = projectApplicationService.findAllByProjectId(pageable, projectId);
        else projectApplicationResponseDTO = projectApplicationService.findAll(pageable);

        return projectApplicationResponseDTO;
    }

    // Endpoint para agricultores
    @GetMapping("/mine/page")
    @PreAuthorize("hasRole('FARMER')")
    public Page<ProjectApplicationResponseDTO> getMyProjectApplications(
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return projectApplicationService.findAllByUserId(pageable, user.getId());
    }

    @GetMapping("/{id}")
    public ProjectApplicationResponseDTO getProjectApplicationById(@PathVariable Integer id) {
        return projectApplicationService.findById(id);
    }

    @PostMapping()
    public ProjectApplicationResponseDTO saveProjectApplication(@RequestBody @Valid ProjectApplicationRequestDTO projectApplicationRequestDTO) {
        return projectApplicationService.save(projectApplicationRequestDTO);
    }

    @PutMapping("/{id}")
    public ProjectApplicationResponseDTO updateProjectApplication(@RequestBody @Valid ProjectApplicationRequestDTO projectApplicationRequestDTO,
                                              @PathVariable Integer id) {
        return projectApplicationService.update(id, projectApplicationRequestDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProjectApplication(@PathVariable Integer id) {
        return projectApplicationService.delete(id);
    }

    @PostMapping("/{id}/reject")
    public Map<String, String> rejectProjectApplication(@PathVariable Integer id) throws MessagingException {
        return projectApplicationService.reject(id);
    }

}
