package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Project;
import com.agap.management.domain.entities.ProjectApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProjectApplicationRepository extends JpaRepository<ProjectApplication, Integer> {

    Page<ProjectApplication> findByApplicantId(Pageable pageable, Integer applicantId);
    Page<ProjectApplication> findByProjectId(Pageable pageable, Integer projectId);

    List<ProjectApplication> findByProject_Id(Integer projectId);


}
