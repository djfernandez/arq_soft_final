package com.tecsup.app.micro.pago.domain.repository;

import java.util.List;
import java.util.Optional;

import com.tecsup.app.micro.pago.domain.model.Payment;

/**
 * Puerto del Repositorio de Producto (Interface)
 * Define el contrato para la persistencia sin depender de la implementación
 * Esta interfaz pertenece al dominio y será implementada en la capa de
 * infraestructura
 */
public interface PaymentRepository {

    /**
     * Obtiene todos los productos
     */
    List<Payment> findAll();

    /**
     * Busca un producto por ID
     */
    Optional<Payment> findById(Long id);

    /**
     * Busca productos por el usuario que los creó
     */
    List<Payment> findByUserId(Long userId);

    /**
     * Guarda un nuevo producto o actualiza uno existente
     */
    Payment save(Payment product);

    /**
     * Elimina un producto por ID
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un producto con el ID dado
     */
    boolean existsById(Long id);

    /**
     * Obtiene todos los productos
     */
    List<Payment> findAllById(List<Long> ids);
}
