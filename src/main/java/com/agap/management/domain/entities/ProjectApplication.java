package com.agap.management.domain.entities;

import com.agap.management.domain.enums.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_application")
public class ProjectApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;



    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "review_date")
    private LocalDate reviewDate;




    // faltan las entidades necesarias para luego crear un crop
    @NotBlank(message = "El campo Nombre de Finca es obligatorio")
    @Size(max = 100, message = "El campo Nombre de Finca no puede tener más de {max} caracteres")
    @Column(name = "farm_name", nullable = false, length = 100)
    private String farmName;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "area", nullable = false)
    private int area;

    @NotBlank(message = "El campo Municipio es obligatorio")
    @Size(max = 100, message = "El campo Municipio no puede tener más de {max} caracteres")
    @Column(name = "municipality", nullable = false, length = 100)
    private String municipality;

    @Size(max = 100, message = "El campo Clima no puede tener más de {max} caracteres.")
    @Column(name = "weather", length = 100)
    private String weather;

}
