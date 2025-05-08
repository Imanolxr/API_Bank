package com.SaintPatrick.Banco.Saint.Patrick.application.service;

import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.InsufficientBalanceException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.InvalidCardException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.exception.NotFoundException;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.input.TransactionServicePort;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.CardRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.TransactionRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService implements TransactionServicePort {
    private final CardRepositoryPort cardRepo;
    private final TransactionRepositoryPort transactionRepo;

    public TransactionService(CardRepositoryPort cardRepo, TransactionRepositoryPort transactionRepo) {
        this.cardRepo = cardRepo;
        this.transactionRepo = transactionRepo;
    }



    public Transaction createTransaction(String cardNumberToCredit, BigDecimal amountOfTransaction, String cardNumberToDebit){

        //verifica existencia tarjetas
        Card cardToDebit = cardRepo.findByCardNumber(cardNumberToDebit).orElseThrow(() -> new InvalidCardException("Tarjeta de origen no encontrada"));
        Card cardToCredit = cardRepo.findByCardNumber(cardNumberToCredit).orElseThrow(() -> new InvalidCardException("Tarjeta de destino no encontrada"));


        //verifica saldo en la tarjeta a debitar
        if(cardToDebit.getBalance().compareTo(amountOfTransaction) < 0){
            throw new InsufficientBalanceException("Saldo insuficiente en la cuenta a debitar");
        }

        //realiza la transaccion
        cardToDebit.debit(amountOfTransaction);
        cardToCredit.credit(amountOfTransaction);

        //actualiza los datos de las tarjetas en base de datos
        cardRepo.updateCard(cardToDebit);
        cardRepo.updateCard(cardToCredit);

        Transaction transaction = new Transaction(amountOfTransaction,LocalDateTime.now(),cardToDebit, cardToCredit);
        transactionRepo.newTransaction(transaction, cardNumberToDebit);

        return transaction;
    }


    public List<Transaction> getMonthList(Integer month, String cardNumber) {
        Card card = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Tarjeta de origen no encontrada"));


        return transactionRepo.transactionsByMonth(month,cardNumber);
    }

    public List<Transaction> getAllMovements(String cardNumber){

        Card card = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Tarjeta de origen no encontrada"));

        return  transactionRepo.allMovements(cardNumber);
    }


    public BigDecimal getMonthSpent(String cardNumber) {
        Card card = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Tarjeta no encontrada"));
        return transactionRepo.getMonthSpent(cardNumber);

    }

    public BigDecimal getMonthIncome(String cardNumber) {
        Card card = cardRepo.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidCardException("Tarjeta no encontrada"));
        return transactionRepo.getMonthIncome(cardNumber);
    }
}
