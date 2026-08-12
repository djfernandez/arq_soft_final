package com.tecsup.app.micro.entrega.domain.repository;

import com.tecsup.app.micro.entrega.domain.model.Delivery;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del Repositorio de Producto (Interface)
 * Define el contrato para la persistencia sin depender de la implementación
 * Esta interfaz pertenece al dominio y será implementada en la capa de
 * infraestructura
 */
public interface DeliveryRepository {

    /**
     * Obtiene todos los productos
     */
    List<Delivery> findAll();

    /**
     * Busca un producto por ID
     */
    Optional<Delivery> findById(Long id);

    /**
     * Busca productos por el usuario que los creó
     */
    List<Delivery> findByUserId(Long userId);

    /**
     * Guarda un nuevo producto o actualiza uno existente
     */
    Delivery save(Delivery product);

    /**
     * Elimina un producto por ID
     */
    void deleteById(Long id);

    /**
     * Verifica si existe un producto con el ID dado
     */
    boolean existsById(Long id);

}
