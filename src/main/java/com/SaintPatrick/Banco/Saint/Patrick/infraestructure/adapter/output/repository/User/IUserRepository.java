package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.User;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
