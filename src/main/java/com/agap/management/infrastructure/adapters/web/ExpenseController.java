package com.agap.management.infrastructure.adapters.web;

import com.agap.management.application.ports.IExpenseService;
import com.agap.management.domain.dtos.request.ExpenseRequestDTO;
import com.agap.management.domain.dtos.response.ExpenseResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
@PreAuthorize("hasRole('ADMIN')")
public class ExpenseController {

    private final IExpenseService expenseService;

    @GetMapping
    public List<ExpenseResponseDTO> getExpenses() {
        return expenseService.findAll();
    }

    @GetMapping("/page")
    public Page<ExpenseResponseDTO> getExpensesPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return expenseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ExpenseResponseDTO getExpenseById(@PathVariable Integer id) {
        return expenseService.findById(id);
    }

    @PostMapping()
    public ExpenseResponseDTO saveExpense(@RequestBody @Valid ExpenseRequestDTO expenseRequestDTO) {
        return expenseService.save(expenseRequestDTO);
    }

    @PutMapping("/{id}")
    public ExpenseResponseDTO updateExpense(@RequestBody @Valid ExpenseRequestDTO expenseRequestDTO,
                                               @PathVariable Integer id) {
        return expenseService.update(id, expenseRequestDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteExpense(@PathVariable Integer id) {
        return expenseService.delete(id);
    }

}
