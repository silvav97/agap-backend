package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExpenseType {
    MANO_DE_OBRA("Gasto de Mano de Obra"),
    PESTICIDA("Gasto de Pesticida"),
    FERTILIZANTE("Gasto de Fertilizante");

    private final String displayValue;
}
