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
@Table(name = "crop_report")
public class CropReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "crop_id", nullable = false)
    private Integer cropId;

    @ManyToOne
    @JoinColumn(name = "crop_id", insertable = false, updatable = false)
    private Crop crop;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "total_sale", nullable = false)
    private float totalSale;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "assigned_budget", nullable = false)
    private float assignedBudget;

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
