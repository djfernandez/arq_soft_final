package com.tecsup.app.micro.entrega.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecsup.app.micro.entrega.infrastructure.persistence.entity.CatalogEntity;

/**
 * Repositorio JPA de Producto
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaCatalogRepository extends CrudRepository<CatalogEntity, Long> {

    List<CatalogEntity> findByCategory(String category);

    List<CatalogEntity> findByCreatedBy(Long userId);

    @Query("SELECT p FROM CatalogEntity p WHERE p.stock > 0")
    List<CatalogEntity> findAvailableCatalogs();

    List<CatalogEntity> findByIdIn(List<Long> ids);
}
