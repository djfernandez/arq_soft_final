package com.tecsup.app.micro.catalogo.domain.exception;

/**
 * Excepción cuando no se encuentra un producto
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
