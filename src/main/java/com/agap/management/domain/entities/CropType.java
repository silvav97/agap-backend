package com.agap.management.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crop_type")
public class CropType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100, message = "El campo Clima no puede tener más de {max} caracteres.")
    @Column(name = "weather", length = 100)
    private String weather;

    @NotBlank(message = "El campo Nombre es obligatorio.")
    @Size(max = 100, message = "El campo Nombre no puede tener más de {max} caracteres.")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "plant_quantity_per_square_meter", nullable = false)
    private int plantQuantityPerSquareMeter;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "harvest_time", nullable = false)
    private int harvestTime;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "fertilizer_quantity_per_plant", nullable = false)
    private int fertilizerQuantityPerPlant;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "fertilizer_frequency", nullable = false)
    private int fertilizerFrequency;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "pesticide_quantity_per_plant", nullable = false)
    private int pesticideQuantityPerPlant;

    @Min(value = 1, message = "El valor debe ser mayor que cero")
    @Column(name = "pesticide_frequency", nullable = false)
    private int pesticideFrequency;

    @ManyToOne
    @JoinColumn(name = "fertilizer_id") //, insertable = false, updatable = false)
    private Fertilizer fertilizer;

    @ManyToOne
    @JoinColumn(name = "pesticide_id") //, insertable = false, updatable = false)
    private Pesticide pesticide;
}
