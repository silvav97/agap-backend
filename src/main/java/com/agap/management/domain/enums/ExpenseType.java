package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseType {
    LABOR("Gasto de Mano de Obra"),
    PESTICIDE("Gasto de Pesticida"),
    FERTILIZER("Gasto de Fertilizante");

    private final String displayValue;
}
