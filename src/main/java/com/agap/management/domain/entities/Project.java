package com.agap.management.domain.entities;

import com.agap.management.domain.enums.ProcessStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "project")
    private List<ProjectApplication> projectApplications;

    @ManyToOne
    @JoinColumn(name = "crop_type_id", nullable = true)  //, insertable = false, updatable = false)
    private CropType cropType;

    @NotBlank(message = "El campo Proyecto es obligatorio")
    @Size(max = 100, message = "El campo Proyecto no puede tener más de {max} caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 100, message = "El campo Municipio no puede tener más de {max} caracteres")
    @Column(name = "municipality", length = 100)
    private String municipality;

    @OneToMany(mappedBy = "project")
    private List<Crop> cropList;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "total_budget", nullable = false)
    private float totalBudget;
}
