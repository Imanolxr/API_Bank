package com.SaintPatrick.Banco.Saint.Patrick.application.service;

import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.CardRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CardRepositoryPort cardRepo;
    private final PasswordEncoder passwordEncoder;


    public AuthService(CardRepositoryPort cardRepo, PasswordEncoder passwordEncoder) {
        this.cardRepo = cardRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String cardNumber, String pin){
        return cardRepo.findByCardNumber(cardNumber).map(card -> passwordEncoder.matches(pin, card.getPin())).orElse(false);
    }
}
