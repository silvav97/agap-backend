package com.agap.management.domain.entities;

import com.agap.management.domain.enums.ApplicationStatus;
import jakarta.persistence.*;
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
    private Project project;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;

    @OneToOne(mappedBy = "application")
    private Crop crop;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status", nullable = false)
    private ApplicationStatus applicationStatus;

    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    @Size(max = 500, message = "El comentario no puede tener más de {max} caracteres")
    @Column(name = "admin_comment", length = 500)
    private String adminComment;

}
