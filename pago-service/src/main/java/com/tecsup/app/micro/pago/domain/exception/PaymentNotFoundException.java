package com.tecsup.app.micro.pago.domain.exception;

/**
 * Excepción cuando no se encuentra un producto
 */
public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
