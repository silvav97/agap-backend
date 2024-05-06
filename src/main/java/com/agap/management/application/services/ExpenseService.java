package com.agap.management.application.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.ports.IExpenseService;
import com.agap.management.domain.dtos.request.ExpenseRequestDTO;
import com.agap.management.domain.dtos.response.ExpenseResponseDTO;
import com.agap.management.domain.entities.Expense;
import com.agap.management.exceptions.personalizedException.EntityNotFoundByFieldException;
import com.agap.management.infrastructure.adapters.persistence.IExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {

    private final IExpenseRepository expenseRepository;
    private final ICropService cropService;
    private final ModelMapper modelMapper;

    @Override
    public List<ExpenseResponseDTO> findAll() {
        return expenseRepository.findAll().stream()
                .map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ExpenseResponseDTO> findAll(Pageable pageable) {
        Page<Expense> page = expenseRepository.findAll(pageable);
        return page.map(expense -> modelMapper.map(expense, ExpenseResponseDTO.class));
    }

    @Override
    public ExpenseResponseDTO findById(Integer id) {
        return modelMapper.map(getExpenseById(id), ExpenseResponseDTO.class);
    }

    @Override
    public ExpenseResponseDTO save(ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = modelMapper.map(expenseRequestDTO, Expense.class);
        expense.setCrop(cropService.getCropById(expenseRequestDTO.getCropId()));

        return modelMapper.map(expenseRepository.save(expense), ExpenseResponseDTO.class);
    }

    @Override
    public ExpenseResponseDTO update(Integer id, ExpenseRequestDTO expenseRequestDTO) {
        Expense expense = getExpenseById(id);
        expense.setCrop(cropService.getCropById(expenseRequestDTO.getCropId()));

        modelMapper.map(expenseRequestDTO, expense);

        Expense savedExpense = expenseRepository.save(expense);
        return modelMapper.map(savedExpense, ExpenseResponseDTO.class);
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            expenseRepository.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public Expense getExpenseById(Integer id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundByFieldException("Gasto", "id", id.toString()));
    }

}
