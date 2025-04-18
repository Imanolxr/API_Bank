package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CardResponseMapper.class)

public interface TransactionDTOMapper {
    @Mapping(target = "originCard", source = "originCard.cardNumber")
    @Mapping(target = "nameOriginCard", expression = "java(transaction.getOriginCard().getUser().getName() + \" \" + transaction.getOriginCard().getUser().getLastName())")

    @Mapping(target = "destinyCard", source = "destinyCard.cardNumber")
    @Mapping(target = "nameDestinyCard", expression = "java(transaction.getDestinyCard().getUser().getName() + \" \" + transaction.getDestinyCard().getUser().getLastName())")
    TransactionDTO internalToDTO(Transaction transaction);

    default TransactionDTO toDTO(Transaction transaction, String loggedCardnumber){
        TransactionDTO dto = internalToDTO(transaction);
        dto.setCredit(transaction.getDestinyCard().getCardNumber().equals(loggedCardnumber));
        return dto;

    }


}
