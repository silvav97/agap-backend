package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessStatus {
    CREATED("Creado"),
    CLOSED("Cerrado");

    private final String displayValue;
}
