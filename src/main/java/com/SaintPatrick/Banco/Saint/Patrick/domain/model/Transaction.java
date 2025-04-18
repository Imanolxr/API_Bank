package com.SaintPatrick.Banco.Saint.Patrick.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {

    private BigDecimal amount;
    private LocalDateTime dateTime;
    private Card originCard;
    private Card destinyCard;

    public Transaction(BigDecimal amount, LocalDateTime dateTime, Card originCard, Card destinyCard) {
        if(amount == null || amount.compareTo(new BigDecimal("0.10")) <0){
            throw new IllegalArgumentException("El monto debe ser al menos 0.10");
        }
        if(originCard == null || originCard.getCardNumber().length() != 16 || !originCard.getCardNumber().matches("\\d{16}")){
            throw new IllegalArgumentException("El número de tarjeta de origen debe tener 16 digitos");
        }
        if(destinyCard == null || destinyCard.getCardNumber().length() != 16 || !destinyCard.getCardNumber().matches("\\d{16}")){
            throw new IllegalArgumentException("El número de tarjeta de destino debe tener 16 digitos");
        }

        this.amount = amount;
        this.dateTime = dateTime;
        this.originCard = originCard;
        this.destinyCard = destinyCard;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Card getOriginCard() {
        return originCard;
    }

    public Card getDestinyCard() {
        return destinyCard;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setOriginCard(Card originCard) {
        this.originCard = originCard;
    }

    public void setDestinyCard(Card destinyCard) {
        this.destinyCard = destinyCard;
    }
}
