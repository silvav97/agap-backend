package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProjectApplicationRepository extends JpaRepository<ProjectApplication, Integer> {

}
