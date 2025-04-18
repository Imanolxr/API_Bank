package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.user.UserRequestDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor

public class CardRequestDTO {
    private String cardNumber;
    private String pin;
    private BigDecimal balance;
    private UserRequestDTO user;

    public CardRequestDTO(String cardNumber, String pin, BigDecimal balance, UserRequestDTO user) {
        if(cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d{16}")){
            throw new IllegalArgumentException("El número de tarjeta debe tener 16 digitos");
        }
        if(pin == null || pin.length() != 4 || !pin.matches("\\d{4}")){
            throw new IllegalArgumentException("El PIN debe tener 4 dígitos");
        }
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El balance no puede ser negativo");
        }
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.user = user;
    }
}
