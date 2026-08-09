package com.tecsup.app.micro.catalogo.domain.exception;

/**
 * Excepción cuando hay error al comunicarse con el servicio de usuarios
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
