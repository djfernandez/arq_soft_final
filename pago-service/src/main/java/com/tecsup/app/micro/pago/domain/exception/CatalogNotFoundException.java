package com.tecsup.app.micro.pago.domain.exception;

/**
 * Excepción cuando no se encuentra un producto
 */
public class CatalogNotFoundException extends RuntimeException {

    public CatalogNotFoundException(String message) {
        super(message);
    }

    public CatalogNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
