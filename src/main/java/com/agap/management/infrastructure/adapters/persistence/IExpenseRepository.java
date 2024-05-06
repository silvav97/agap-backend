package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
}
