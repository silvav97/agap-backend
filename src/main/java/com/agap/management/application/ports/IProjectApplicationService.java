package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.ProjectApplicationRequestDTO;
import com.agap.management.domain.dtos.response.ProjectApplicationResponseDTO;
import com.agap.management.domain.entities.ProjectApplication;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface IProjectApplicationService {
    List<ProjectApplicationResponseDTO> findAll();
    Page<ProjectApplicationResponseDTO> findAll(Pageable pageable);
    Page<ProjectApplicationResponseDTO> findAllByUserId(Pageable pageable, Integer userId);
    Page<ProjectApplicationResponseDTO> findAllByProjectId(Pageable pageable, Integer projectId);
    ProjectApplicationResponseDTO findById(Integer id);
    ProjectApplicationResponseDTO save(ProjectApplicationRequestDTO projectApplicationRequestDTO);
    ProjectApplication getProjectApplicationById(Integer id);
    ProjectApplicationResponseDTO update(Integer id, ProjectApplicationRequestDTO projectApplicationRequestDTO);
    Boolean delete(Integer id);
    Map<String, String> reject(Integer id) throws MessagingException;
}
