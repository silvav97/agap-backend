package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Fertilizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFertilizerRepository extends JpaRepository<Fertilizer, Integer> {
}
