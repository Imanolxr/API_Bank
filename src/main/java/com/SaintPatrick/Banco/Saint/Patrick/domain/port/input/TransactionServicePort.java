package com.SaintPatrick.Banco.Saint.Patrick.domain.port.input;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionServicePort {

    Transaction createTransaction(String cardNumberToCredit, BigDecimal amountOfTransaction, String cardNumberToDebit);
    List<Transaction> getMonthList(Integer month, String cardNumber);
    List<Transaction> getAllMovements(String cardNumber);
}
