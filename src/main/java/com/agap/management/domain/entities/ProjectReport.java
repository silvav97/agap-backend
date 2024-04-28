package com.agap.management.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_report")
public class ProjectReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "total_sale", nullable = false)
    private float totalSale;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "total_budget", nullable = false)
    private float totalBudget;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "expected_expense", nullable = false)
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "real_expense", nullable = false)
    private float realExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "profit", nullable = false)
    private float profit;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "profitability", nullable = false)
    private float profitability;
}
