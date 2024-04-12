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
@Table(name = "fertilizer")
public class Fertilizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El campo Nombre es obligatorio")
    @Size(max = 100, message = "El campo Nombre no puede tener más de {max} caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El campo Marca es obligatorio")
    @Size(max = 100, message = "El campo Marca no puede tener más de {max} caracteres.")
    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Min(value = 0, message = "El valor debe ser mayor que cero")
    @Column(name = "price_per_gram", nullable = false)
    private float pricePerGram;

}
