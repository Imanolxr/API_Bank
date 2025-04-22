package com.SaintPatrick.Banco.Saint.Patrick.domain.port.input;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;

import java.util.Optional;

public interface CardServicePort {

    void newCard(Card newCard);
    Optional<Card> findByCardNumber(String cardNumber);
}
