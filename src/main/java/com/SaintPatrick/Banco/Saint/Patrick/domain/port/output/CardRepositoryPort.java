package com.SaintPatrick.Banco.Saint.Patrick.domain.port.output;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;

import java.math.BigDecimal;
import java.util.Optional;

public interface CardRepositoryPort {

    void saveNewCard(Card card);

    Optional<Card> findByCardNumber(String cardNumber);

    void credit(BigDecimal amount, String cardNumber);

    void debit(BigDecimal amount, String cardNumber);

    void updateCard(Card cardToDebit);

    boolean existByCardNumber(String cardNumber);
}
