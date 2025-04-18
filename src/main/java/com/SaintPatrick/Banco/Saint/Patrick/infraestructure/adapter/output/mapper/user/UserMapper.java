package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.user;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.User;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);
    User toModel(UserEntity entity);
}