package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.Login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardLoginRequest {
    private String cardNumber;
    private String pin;
}
