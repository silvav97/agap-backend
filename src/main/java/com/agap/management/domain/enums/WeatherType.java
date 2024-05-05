package com.agap.management.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeatherType {
    CALIDO("Cálido"),
    FRIO("Frío"),
    TROPICAL("Tropical"),
    TEMPLADO("Templado");

    private final String displayValue;
}
