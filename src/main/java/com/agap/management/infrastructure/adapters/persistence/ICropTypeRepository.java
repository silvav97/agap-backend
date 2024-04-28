package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.CropType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICropTypeRepository extends JpaRepository<CropType, Integer> {
}
