package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity;


import jakarta.persistence.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.10", message = "Cantidad debe ser mayor a cero")
    private BigDecimal amount;

    private LocalDateTime dateTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    @NotBlank(message = "id de origen de la transferencia necesario")
    private CardEntity originCardEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "destiny_id", referencedColumnName = "id")
    @NotBlank(message = "id de destino de la transferencia necesario")
    private CardEntity destinyCardEntity;
}
