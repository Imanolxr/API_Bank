package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CardResponseDTO {
    private String cardNumber;
    private BigDecimal balance;
    private User user;


    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16) {
            return "**** **** **** ****";
        }
        return "**** **** **** " + cardNumber.substring(12);
    }




}
