package com.agap.management.services;

import com.agap.management.application.ports.ICropService;
import com.agap.management.application.services.ExpenseService;
import com.agap.management.domain.dtos.request.ExpenseRequestDTO;
import com.agap.management.domain.dtos.response.ExpenseResponseDTO;
import com.agap.management.domain.entities.Expense;
import com.agap.management.domain.entities.Crop;
import com.agap.management.infrastructure.adapters.persistence.IExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock private IExpenseRepository expenseRepository;
    @Mock private ICropService cropService;
    @Mock private ModelMapper modelMapper;
    @InjectMocks private ExpenseService expenseService;

    private Expense expense;
    private ExpenseRequestDTO expenseRequestDTO;
    private ExpenseResponseDTO expenseResponseDTO;
    private Crop crop;

    @BeforeEach
    void setUp() {
        expense = new Expense();
        expense.setId(1);

        expenseRequestDTO = new ExpenseRequestDTO();
        expenseRequestDTO.setCropId(1);

        expenseResponseDTO = new ExpenseResponseDTO();
        expenseResponseDTO.setId(1);

        crop = new Crop();
        crop.setId(1);
    }

    @Test
    void testFindAll() {
        when(expenseRepository.findAll()).thenReturn(Collections.singletonList(expense));
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        List<ExpenseResponseDTO> result = expenseService.findAll();
        assertEquals(1, result.size());
        assertEquals(expenseResponseDTO, result.get(0));
    }

    @Test
    void testFindAllPageable() {
        Page<Expense> page = new PageImpl<>(Collections.singletonList(expense));
        when(expenseRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        Page<ExpenseResponseDTO> result = expenseService.findAll(PageRequest.of(0, 10));
        assertEquals(1, result.getContent().size());
        assertEquals(expenseResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindAllByCropId() {
        Page<Expense> page = new PageImpl<>(Collections.singletonList(expense));
        when(expenseRepository.findByCropId(any(Pageable.class), anyInt())).thenReturn(page);
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        Page<ExpenseResponseDTO> result = expenseService.findAllByCropId(PageRequest.of(0, 10), 1);
        assertEquals(1, result.getContent().size());
        assertEquals(expenseResponseDTO, result.getContent().get(0));
    }

    @Test
    void testFindById() {
        when(expenseRepository.findById(anyInt())).thenReturn(Optional.of(expense));
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        ExpenseResponseDTO result = expenseService.findById(1);
        assertEquals(expenseResponseDTO, result);
    }

    @Test
    void testSave() {
        when(modelMapper.map(any(ExpenseRequestDTO.class), eq(Expense.class))).thenReturn(expense);
        when(cropService.getCropById(anyInt())).thenReturn(crop);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        ExpenseResponseDTO result = expenseService.save(expenseRequestDTO);
        assertEquals(expenseResponseDTO, result);
    }

    @Test
    void testUpdate() {
        when(expenseRepository.findById(anyInt())).thenReturn(Optional.of(expense));
        when(cropService.getCropById(anyInt())).thenReturn(crop);
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);
        doAnswer(invocation -> {
            invocation.getArgument(0);
            Expense entity = invocation.getArgument(1);
            entity.setCrop(crop);
            return null;
        }).when(modelMapper).map(any(ExpenseRequestDTO.class), any(Expense.class));
        when(modelMapper.map(any(Expense.class), eq(ExpenseResponseDTO.class))).thenReturn(expenseResponseDTO);

        ExpenseResponseDTO result = expenseService.update(1, expenseRequestDTO);
        assertEquals(expenseResponseDTO, result);
    }

    @Test
    void testDelete() {
        doNothing().when(expenseRepository).deleteById(anyInt());

        Boolean result = expenseService.delete(1);
        assertTrue(result);
        verify(expenseRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetExpenseById() {
        when(expenseRepository.findById(anyInt())).thenReturn(Optional.of(expense));

        Expense result = expenseService.getExpenseById(1);
        assertEquals(expense, result);
    }
}
