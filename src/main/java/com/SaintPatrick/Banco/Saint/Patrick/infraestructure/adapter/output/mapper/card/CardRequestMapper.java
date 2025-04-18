package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.user.UserRequestMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.card.CardRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring", uses = UserRequestMapper.class)
public interface CardRequestMapper {
    @Mapping(target = "pin", expression = "java(encryptPin(dto.getPin()))")
    Card toModel(CardRequestDTO dto);

    default String encryptPin(String pin) {
        return new BCryptPasswordEncoder().encode(pin);
    }
}
