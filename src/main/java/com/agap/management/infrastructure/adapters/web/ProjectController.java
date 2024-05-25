package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IProjectService;
import com.agap.management.application.ports.IStorageService;
import com.agap.management.domain.dtos.request.ProjectRequestDTO;
import com.agap.management.domain.dtos.response.CropResponseDTO;
import com.agap.management.domain.dtos.response.ProjectResponseDTO;
import com.agap.management.domain.entities.Project;
import com.agap.management.infrastructure.adapters.persistence.IProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final IProjectService projectService;
    private final IStorageService storageService;
    private final IProjectRepository projectRepository;

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

    @GetMapping("/{id}/relatedCrops")
    public List<CropResponseDTO> getRelatedCropsByProjectId(@PathVariable Integer id) {
        return projectService.findRelatedCrops(id);
    }

    @PostMapping("/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder.fromHttpUrl(host).path("/api/v1/project/imagen/").path(path).toUriString();
        return Map.of("url", url);
    }
    @PostMapping("/upload2")
    public Map<String, String> uploadFile2(@RequestParam("file") MultipartFile multipartFile, @RequestParam("projectId") Integer projectId, HttpServletRequest request) {
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder.fromHttpUrl(host).path("/api/v1/project/imagen/").path(path).toUriString();

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        project.setImageUrl(url);
        projectRepository.save(project);

        return Map.of("url", url);
    }

    @GetMapping("/imagen/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @PostMapping(path = "/save-with-file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ProjectResponseDTO saveProjectAndUploadFile(
            @RequestPart("project") String  projectJson,
            @RequestPart("file") MultipartFile file) {
        ProjectRequestDTO projectRequestDTO;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            projectRequestDTO = objectMapper.readValue(projectJson, ProjectRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
        String imageUrl = storageService.store(file);
        projectRequestDTO.setImageUrl(imageUrl);
        return projectService.save(projectRequestDTO);
    }

    @PutMapping(path = "/update-with-file/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ProjectResponseDTO updateProjectAndUploadFile(
            @RequestPart("project") String projectJson,
            @RequestPart("file") MultipartFile file,
            @PathVariable Integer id) {
        ProjectRequestDTO projectRequestDTO;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            projectRequestDTO = objectMapper.readValue(projectJson, ProjectRequestDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Invalid JSON format", e);
        }
        String imageUrl = storageService.store(file);
        projectRequestDTO.setImageUrl(imageUrl);
        return projectService.update(id, projectRequestDTO);
    }

}
