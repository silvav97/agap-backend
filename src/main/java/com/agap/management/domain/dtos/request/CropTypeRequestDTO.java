package com.agap.management.domain.dtos.request;

import com.agap.management.domain.enums.WeatherType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CropTypeRequestDTO {

    @NonNull
    private WeatherType weather;

    @NotBlank
    @Size(max = 100, message = "El campo Clima no puede tener más de {max} caracteres.")
    private String name;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int plantQuantityPerSquareMeter;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int harvestTime;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int fertilizerQuantityPerPlant;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int fertilizerFrequency;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int pesticideQuantityPerPlant;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    private int pesticideFrequency;

    private int fertilizerId;

    private int pesticideId;

}
