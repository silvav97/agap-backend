package com.agap.management.domain.entities;

import com.agap.management.domain.enums.ExpenseType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "crop_id")
    private Crop crop;

    @DecimalMin(value = "0.000001", message = "El valor debe ser mayor que cero")
    @Column(name = "expense_value", nullable = false)
    private float expenseValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_description", nullable = false)
    private ExpenseType expenseDescription;

    @NotNull(message = "El campo Fecha Gasto es obligatorio.")
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

}
