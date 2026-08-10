package com.tecsup.app.micro.pedido.domain.exception;

public class InvalidOrderDataException extends RuntimeException {

  public InvalidOrderDataException(String message) {
    super(message);
  }

}
