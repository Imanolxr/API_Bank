package com.SaintPatrick.Banco.Saint.Patrick.domain.exception;

public class InvalidCardException extends RuntimeException {
  public InvalidCardException(String message) {
    super(message);
  }
}
