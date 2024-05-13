package com.agap.management.application.ports;

import com.agap.management.domain.dtos.request.ExpenseRequestDTO;
import com.agap.management.domain.dtos.response.ExpenseResponseDTO;
import com.agap.management.domain.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IExpenseService {
    List<ExpenseResponseDTO> findAll();
    Page<ExpenseResponseDTO> findAll(Pageable pageable);

    Page<ExpenseResponseDTO> findAllByCropId(Pageable pageable, Integer cropId);

    ExpenseResponseDTO findById(Integer id);
    ExpenseResponseDTO save(ExpenseRequestDTO expenseRequestDTO);
    ExpenseResponseDTO update(Integer id, ExpenseRequestDTO expenseRequestDTO);
    Boolean delete(Integer id);
    Expense getExpenseById(Integer id);
}
