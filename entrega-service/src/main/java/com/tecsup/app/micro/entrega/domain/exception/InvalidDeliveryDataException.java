package com.tecsup.app.micro.entrega.domain.exception;

/**
 * Excepción cuando los datos del producto son inválidos
 */
public class InvalidDeliveryDataException extends RuntimeException {

    public InvalidDeliveryDataException(String message) {
        super(message);
    }
}
