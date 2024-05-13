package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessStatus {
    CREADO("CREADO"),
    CERRADO("CERRADO");

    private final String displayValue;
}
