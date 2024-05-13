package com.agap.management.infrastructure.adapters.persistence;

import com.agap.management.domain.entities.Expense;
import com.agap.management.domain.entities.ProjectApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExpenseRepository extends JpaRepository<Expense, Integer> {

    Page<Expense> findByCropId(Pageable pageable, Integer cropId);

}
