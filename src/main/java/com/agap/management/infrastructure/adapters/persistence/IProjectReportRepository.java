package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.ProjectReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectReportRepository extends JpaRepository<ProjectReport, Integer> {
}
