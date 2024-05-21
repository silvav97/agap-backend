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

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "total_sale", nullable = false)
    private float totalSale;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero1")
    @Column(name = "assigned_budget", nullable = false)
    private float assignedBudget;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero2")
    @Column(name = "expected_expense", nullable = false)
    private float expectedExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero3")
    @Column(name = "real_expense", nullable = false)
    private float realExpense;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero4")
    @Column(name = "profit", nullable = false)
    private float profit;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero5")
    @Column(name = "profitability", nullable = false)
    private float profitability;
}
