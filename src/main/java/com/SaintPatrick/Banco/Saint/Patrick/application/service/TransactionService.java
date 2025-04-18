package com.SaintPatrick.Banco.Saint.Patrick.application.service;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Card;
import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.CardRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.domain.port.output.TransactionRepositoryPort;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final CardRepositoryPort cardRepo;
    private final TransactionRepositoryPort transactionRepo;

    public TransactionService(CardRepositoryPort cardRepo, TransactionRepositoryPort transactionRepo) {
        this.cardRepo = cardRepo;
        this.transactionRepo = transactionRepo;
    }



    public Transaction createTransaction(String cardNumberToCredit, BigDecimal amountOfTransaction, String cardNumberToDebit){

        //verifica existencia tarjetas
        Card cardToDebit = cardRepo.findByCardNumber(cardNumberToDebit).orElseThrow(() -> new IllegalArgumentException("Tarjeta de origen no encontrada"));
        Card cardToCredit = cardRepo.findByCardNumber(cardNumberToCredit).orElseThrow(() -> new IllegalArgumentException("Tarjeta de destino no encontrada"));


        //verifica saldo en la tarjeta a debitar
        if(cardToDebit.getBalance().compareTo(amountOfTransaction) < 0){
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta a debitar");
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
        return transactionRepo.transactionsByMonth(month,cardNumber);
    }
}
