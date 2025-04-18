package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.transaction;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.TransactionEntity;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card.CardMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface TransactionMapper {

    @Mapping(target = "originCardEntity", source = "originCard")
    @Mapping(target = "destinyCardEntity", source = "destinyCard")
    TransactionEntity toEntity(Transaction transaction);
    Transaction toModel(TransactionEntity entity);


}
