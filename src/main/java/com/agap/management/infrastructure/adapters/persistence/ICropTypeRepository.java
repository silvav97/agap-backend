package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.CropType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICropTypeRepository extends JpaRepository<CropType, Integer> {

    List<CropType> findByFertilizer_Id(Integer fertilizerId);

    List<CropType> findByPesticide_Id(Integer pesticideId);

}
