package com.SaintPatrick.Banco.Saint.Patrick.domain.port.output;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.rest.Dto.transaction.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepositoryPort {

    TransactionDTO newTransaction(Transaction transaction, String cardNumber);
    List<Transaction> allTransactions(String cardNumber);
    List<Transaction> transactionsByDate(String cardNumber, LocalDateTime dateTime);
    List<Transaction> transactionsByMonth(int month, String cardNumber);

}
