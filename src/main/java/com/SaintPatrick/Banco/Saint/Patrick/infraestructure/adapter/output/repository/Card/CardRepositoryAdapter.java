package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.Card;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.CardRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.CardEntity;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card.CardMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.User.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final ICardRepository cardRepo;
    private final CardMapper cardMapper;
    private final IUserRepository userRepo;

    public CardRepositoryAdapter(ICardRepository cardRepo, CardMapper cardMapper, IUserRepository userRepo) {
        this.cardRepo = cardRepo;
        this.cardMapper = cardMapper;
        this.userRepo = userRepo;
    }

    @Override
    public void saveNewCard(Card card) {
        CardEntity cardEntity = cardMapper.toEntity(card);
        if (cardEntity.getUser().getId() == null){
            userRepo.save(cardEntity.getUser());
        }
        cardRepo.save(cardEntity);
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) {
        return cardRepo.findByCardNumber(cardNumber).map(cardMapper::toModel) ;
    }

    @Override
    public void credit(BigDecimal amount, String cardNumber) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        CardEntity entity = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new EntityNotFoundException("Tarjeta numero:" + cardNumber + " no encontrada"));

        Card card = cardMapper.toModel(entity);

        card.setBalance(card.getBalance().add(amount));

        cardRepo.save(cardMapper.toEntity(card));


    }

    @Override
    public void debit(BigDecimal amount, String cardNumber) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        CardEntity entity = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new EntityNotFoundException("Tarjeta numero:" + cardNumber + " no encontrada"));

        Card card = cardMapper.toModel(entity);

        card.setBalance(card.getBalance().subtract(amount));

        cardRepo.save(cardMapper.toEntity(card));

    }

    @Override
    public void updateCard(Card cardToDebit) {
        Optional<CardEntity> optionalEntity = cardRepo.findByCardNumber(cardToDebit.getCardNumber());
        if(optionalEntity.isPresent()){
            CardEntity entity = optionalEntity.get();
            entity.setBalance(cardToDebit.getBalance());
            cardRepo.save(entity);
        } else {
            throw new IllegalArgumentException("No se encontró la tarjeta para actualizar");
        }
    }

    @Override
    public boolean existByCardNumber(String cardNumber) {
        CardEntity entity = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new EntityNotFoundException("Tarjeta numero:" + cardNumber + " no encontrada"));
        return entity != null;
    }


}
