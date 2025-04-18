package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[0-9]{16}$", message = "El numero de la tarjeta se compone por 16 numeros")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^[0-9]{4}$", message = "El pin esta compuesto por 4 numeros")
    private String pin;


    @Min(value = 0)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
