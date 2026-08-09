package com.tecsup.app.micro.catalogo.domain.exception;

/**
 * Excepción cuando los datos del producto son inválidos
 */
public class InvalidCatalogDataException extends RuntimeException {

    public InvalidCatalogDataException(String message) {
        super(message);
    }
}
