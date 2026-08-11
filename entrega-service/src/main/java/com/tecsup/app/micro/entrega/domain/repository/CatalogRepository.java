package com.tecsup.app.micro.entrega.domain.repository;

import com.tecsup.app.micro.entrega.domain.model.Catalog;

import java.util.List;
import java.util.Optional;

/**
 * Puerto del Repositorio de Producto (Interface)
 * Define el contrato para la persistencia sin depender de la implementación
 * Esta interfaz pertenece al dominio y será implementada en la capa de
 * infraestructura
 */
public interface CatalogRepository {

    /**
     * Obtiene todos los productos
     */
    List<Catalog> findAll();

    /**
     * Busca un producto por ID
     */
    Optional<Catalog> findById(Long id);

    /**
     * Busca productos por categoría
     */
    List<Catalog> findByCategory(String category);

    /**
     * Busca productos por el usuario que los creó
     */
    List<Catalog> findByCreatedBy(Long userId);

    /**
     * Busca productos disponibles (stock > 0)
     */
    List<Catalog> findAvailableCatalogs();

    /**
     * Guarda un nuevo producto o actualiza uno existente
     */
    Catalog save(Catalog product);

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
    List<Catalog> findAllById(List<Long> ids);
}
