package com.tecsup.app.micro.pedido.domain.exception;

public class RestaurantNotFoundException extends RuntimeException {

  public RestaurantNotFoundException(String message) {
    super(message);
  }

  public RestaurantNotFoundException(Long id) {
    super("Restaurant not found with id: " + id);
  }

}
