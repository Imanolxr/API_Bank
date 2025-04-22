package com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.repository.Transaction;

import com.SaintPatrick.Banco.Saint.Patrick.domain.model.Transaction;
import com.SaintPatrick.Banco.Saint.Patrick.infraestructure.adapter.output.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<TransactionEntity, Long> {


    @Query("SELECT t FROM TransactionEntity t WHERE MONTH(t.dateTime) = :month AND t.originCardEntity.cardNumber = :cardNumber")
    List<TransactionEntity> findByMonthAndCardNumber(@Param("month") int month, @Param("cardNumber") String cardNumber);

    @Query("SELECT t FROM TransactionEntity t WHERE t.originCardEntity.cardNumber = :cardNumber OR t.destinyCardEntity.cardNumber = :cardNumber")
    List<TransactionEntity> findAllMovementsByCardNumber(@Param("cardNumber") String cardNumber);

}
