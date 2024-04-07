package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Role;
import com.agap.management.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleType name);
}
