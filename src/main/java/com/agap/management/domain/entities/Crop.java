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
@Table(name = "crop")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "project_application_id")
    private ProjectApplication projectApplication;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessStatus status;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "expected_expense", nullable = false)
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "assigned_budget", nullable = false)
    private float assignedBudget;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "sale_value")
    private Float saleValue;

    @OneToMany(mappedBy = "crop")
    private List<Expense> expenseList;

}
