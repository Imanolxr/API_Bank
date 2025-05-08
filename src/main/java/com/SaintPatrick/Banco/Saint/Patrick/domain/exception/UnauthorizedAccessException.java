package com.SaintPatrick.Banco.Saint.Patrick.domain.exception;

public class UnauthorizedAccessException extends RuntimeException {
  public UnauthorizedAccessException(String message) {
    super(message);
  }
}
