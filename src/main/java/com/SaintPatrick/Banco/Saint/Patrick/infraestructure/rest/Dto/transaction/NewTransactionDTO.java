package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewTransactionDTO {

        private String originCardNumber;
        private String destinyCardNumber;
        private BigDecimal amount;


}
