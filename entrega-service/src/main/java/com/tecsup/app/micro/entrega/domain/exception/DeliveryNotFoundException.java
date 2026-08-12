package com.tecsup.app.micro.entrega.domain.exception;

/**
 * Excepción cuando no se encuentra un producto
 */
public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException(String message) {
        super(message);
    }

    public DeliveryNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
