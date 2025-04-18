package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.Transaction;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.TransactionRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.input.TransactionDTOMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.CardEntity;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.TransactionEntity;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.card.CardMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.mapper.transaction.TransactionMapper;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.Card.ICardRepository;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionMapper mapper;
    private final ITransactionRepository transactionRepo;
    private final TransactionDTOMapper dtoMapper;
    private final ICardRepository cardRepo;
    private final CardMapper cardMapper;

    public TransactionRepositoryAdapter(TransactionMapper mapper, ITransactionRepository transactionRepo, TransactionDTOMapper dtoMapper, ICardRepository cardRepo, CardMapper cardMapper) {
        this.mapper = mapper;
        this.transactionRepo = transactionRepo;
        this.dtoMapper = dtoMapper;
        this.cardRepo = cardRepo;
        this.cardMapper = cardMapper;
    }

    @Override
    public TransactionDTO newTransaction(Transaction transaction, String cardNumber) {

        CardEntity cardToDebitEntity = cardRepo.findByCardNumber(transaction.getOriginCard().getCardNumber()).orElseThrow(() -> new IllegalArgumentException("Tarjeta origen no encontrada en base de datos"));
        CardEntity cardToCreditEntity = cardRepo.findByCardNumber(transaction.getDestinyCard().getCardNumber()).orElseThrow(() -> new IllegalArgumentException("Tarjeta de destino no encontrada en base de datos"));

        TransactionEntity entity = new TransactionEntity();
        entity.setAmount(transaction.getAmount());
        entity.setDateTime(transaction.getDateTime());


        entity.setOriginCardEntity(cardToDebitEntity);
        entity.setDestinyCardEntity(cardToCreditEntity);

        transactionRepo.save(entity);

        return dtoMapper.toDTO(transaction, cardNumber);
    }

    @Override
    public List<Transaction> allTransactions(String cardNumber) {
        return List.of();
    }

    @Override
    public List<Transaction> transactionsByDate(String cardNumber, LocalDateTime dateTime) {
        return List.of();
    }

    @Override
    public List<Transaction> transactionsByMonth(int month, String cardNumber) {
        return transactionRepo.findByMonthAndCardNumber(month, cardNumber);
    }


}
