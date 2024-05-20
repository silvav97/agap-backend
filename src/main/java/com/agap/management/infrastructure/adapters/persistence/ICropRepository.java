package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Crop;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ICropRepository extends JpaRepository<Crop, Integer> {

    Page<Crop> findByProjectApplication_Applicant_Id(Pageable pageable, Integer userId);

    List<Crop> findByProjectApplication_Project_Id(Integer projectId);


}
