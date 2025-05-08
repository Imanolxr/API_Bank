package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.user;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.User;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.user.UserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {
    User toModel(UserRequestDTO dto);
    UserRequestDTO toDto (User user);
}
