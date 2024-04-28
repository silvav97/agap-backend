package com.agap.management.domain.entities;

import jakarta.persistence.*;
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
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "crop_id", nullable = false)
    private Integer cropId;

    @ManyToOne
    @JoinColumn(name = "crop_id", insertable = false, updatable = false)
    private Crop crop;

    @NotBlank(message = "El campo Mensaje del Título es obligatorio.")
    @Size(max = 255, message = "El campo Mensaje del Título no puede tener más de {max} caracteres.")
    @Column(name = "title_message", nullable = false, length = 255)
    private String titleMessage;

    @NotBlank(message = "El campo Mensaje del Cuerpo es obligatorio.")
    @Size(max = 500, message = "El campo Mensaje del Cuerpo no puede tener más de {max} caracteres.")
    @Column(name = "body_message", nullable = false, length = 500)
    private String bodyMessage;

    @Column(name = "periodicity", nullable = false)
    private int periodicity;
}
