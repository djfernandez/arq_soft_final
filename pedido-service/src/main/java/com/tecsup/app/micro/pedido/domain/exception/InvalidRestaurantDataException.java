package com.tecsup.app.micro.pedido.domain.exception;

public class InvalidRestaurantDataException extends RuntimeException {

  public InvalidRestaurantDataException(String message) {
    super(message);
  }

}