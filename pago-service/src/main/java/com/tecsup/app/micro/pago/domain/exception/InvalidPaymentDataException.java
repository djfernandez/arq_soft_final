package com.tecsup.app.micro.pago.domain.exception;

/**
 * Excepción cuando los datos del producto son inválidos
 */
public class InvalidPaymentDataException extends RuntimeException {

    public InvalidPaymentDataException(String message) {
        super(message);
    }
}
