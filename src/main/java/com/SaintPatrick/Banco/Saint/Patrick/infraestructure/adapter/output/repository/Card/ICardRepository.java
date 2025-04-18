package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.Card;

import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
}
