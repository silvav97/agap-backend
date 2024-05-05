package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.CropType;
import com.agap.management.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByCropType_Id(Integer cropTypeId);

}
