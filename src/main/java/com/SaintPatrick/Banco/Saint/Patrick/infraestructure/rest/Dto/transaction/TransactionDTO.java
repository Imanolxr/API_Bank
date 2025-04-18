package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card.CardResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String originCard;
    private String nameOriginCard;
    private String destinyCard;
    private String nameDestinyCard;
    private boolean credit;


    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return "**** **** **** ****";
        }
        return "**** **** **** " + cardNumber.substring(12);
    }


}
