package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.CropReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICropReportRepository extends JpaRepository<CropReport, Integer> {
    CropReport findByCrop_Id(Integer crop_id);
    List<CropReport> findAllByCrop_ProjectApplication_Project_Id(Integer project_id);
    Page<CropReport> findAllByCrop_ProjectApplication_Project_Id(Pageable pageable, Integer project_id);
}
