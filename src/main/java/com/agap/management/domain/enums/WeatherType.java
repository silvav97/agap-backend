package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeatherType {
    HOT("Caliente"),
    COLD("Frio"),
    MILD("Templado");

    private final String displayValue;
}
