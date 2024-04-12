package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Pesticide;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPesticideRepository extends JpaRepository<Pesticide, Integer> {
}
