package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.User;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.CardEntity;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.user.UserMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CardMapper {
    CardEntity toEntity(Card card);

    default Card toModel(CardEntity entity) {
        if (entity == null) return null;

        return Card.fromPersistence(
                entity.getCardNumber(),
                entity.getPin(),
                entity.getBalance(),
                entity.getUser() != null ? new User(
                        entity.getUser().getName(),
                        entity.getUser().getLastName()
                ) : null
        );
    }
}
