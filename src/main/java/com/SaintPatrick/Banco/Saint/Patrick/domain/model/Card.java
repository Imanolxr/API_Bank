package com.SaintPatrick.Banco.Saint.Patrick.domain.model;


import java.math.BigDecimal;
import java.util.Objects;

public class Card {
    private String cardNumber;
    private String pin;
    private BigDecimal balance;
    private User user;


    public Card(String cardNumber, String pin, BigDecimal balance, User user) {
        if(cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d{16}")){
            throw new IllegalArgumentException("El número de tarjeta debe tener 16 digitos");
        }
        if(pin == null || pin.length() != 4 || !pin.matches("\\d{4}")){
            throw new IllegalArgumentException("El PIN debe tener 4 dígitos");
        }
        if(balance == null || balance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("El balance no puede ser negativo");
        }
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.user = user;

    }

    // Constructor sin validación del PIN (para usar al recuperar de la DB)
    private Card() {}
    public static Card fromPersistence(String cardNumber, String hashedPin, BigDecimal balance, User user) {
        Card card = new Card();
        card.cardNumber = cardNumber;
        card.pin = hashedPin;
        card.balance = balance;
        card.user = user;
        return card;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public void debit(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        if (amount.compareTo(balance) > 0){
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount){
        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        this.balance = this.balance.add(amount);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return cardNumber.equals(card.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }
}
