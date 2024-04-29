package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICropRepository extends JpaRepository<Crop, Integer> {
}