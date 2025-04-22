package com.SaintPatrick.Banco.Saint.Patrick.application.service;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.input.CardServicePort;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.CardRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService implements CardServicePort {
    private final CardRepositoryPort cardRepo;
    private final PasswordEncoder passwordEncoder;

    public CardService(CardRepositoryPort cardRepo, PasswordEncoder passwordEncoder) {
        this.cardRepo = cardRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void newCard(Card newCard){
        String encodedPin = passwordEncoder.encode(newCard.getPin());
        newCard.setPin(encodedPin);
        cardRepo.saveNewCard(newCard);
    }
    public Optional<Card> findByCardNumber(String cardNumber){

        return cardRepo.findByCardNumber(cardNumber);
    }
}
