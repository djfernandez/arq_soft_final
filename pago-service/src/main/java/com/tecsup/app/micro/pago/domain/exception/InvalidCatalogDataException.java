package com.tecsup.app.micro.pago.domain.exception;

/**
 * Excepción cuando los datos del producto son inválidos
 */
public class InvalidCatalogDataException extends RuntimeException {

    public InvalidCatalogDataException(String message) {
        super(message);
    }
}
