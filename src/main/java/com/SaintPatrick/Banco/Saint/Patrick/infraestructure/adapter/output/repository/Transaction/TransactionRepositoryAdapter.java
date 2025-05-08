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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final ITransactionRepository transactionRepo;
    private final TransactionDTOMapper dtoMapper;
    private final ICardRepository cardRepo;
    private final TransactionMapper transactionMapper;

    public TransactionRepositoryAdapter(ITransactionRepository transactionRepo, TransactionDTOMapper dtoMapper, ICardRepository cardRepo, TransactionMapper transactionMapper) {
        this.transactionRepo = transactionRepo;
        this.dtoMapper = dtoMapper;
        this.cardRepo = cardRepo;
        this.transactionMapper = transactionMapper;
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
    public List<Transaction> allMovements(String cardNumber) {
        List<TransactionEntity> entities = transactionRepo.findAllMovementsByCardNumber(cardNumber);

        return entities.stream()
                .map(transactionMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> transactionsByDate(String cardNumber, LocalDateTime dateTime) {
        return List.of();
    }

    @Override
    public List<Transaction> transactionsByMonth(int month, String cardNumber) {
        List<TransactionEntity> entities = transactionRepo.findByMonthAndCardNumber(month, cardNumber);
        return entities.stream()
                .map(transactionMapper::toModel)
                .collect(Collectors.toList());

    }

    @Override
    public BigDecimal getMonthSpent(String cardNumber) {
        LocalDateTime startOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime endOfMonth = LocalDateTime.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return transactionRepo.getTotalMonthSpent(cardNumber, startOfMonth, endOfMonth)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getMonthIncome(String cardNumber) {
        LocalDateTime startOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        LocalDateTime endOfMonth = LocalDateTime.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        return transactionRepo.getTotalMonthIncome(cardNumber,startOfMonth, endOfMonth).orElse(BigDecimal.ZERO);
    }


}
