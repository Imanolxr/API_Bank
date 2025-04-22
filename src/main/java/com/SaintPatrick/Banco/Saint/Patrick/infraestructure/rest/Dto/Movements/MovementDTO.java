package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.Movements;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDTO {
    private TransactionDTO movement;
    private boolean credit;
}
