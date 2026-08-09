package com.tecsup.app.micro.catalogo.infrastructure.persistence.repository;

import com.tecsup.app.micro.catalogo.infrastructure.persistence.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio JPA de Producto
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaCatalogRepository extends JpaRepository<CatalogEntity, Long> {

    List<CatalogEntity> findByCategory(String category);

    List<CatalogEntity> findByCreatedBy(Long userId);

    @Query("SELECT p FROM CatalogEntity p WHERE p.stock > 0")
    List<CatalogEntity> findAvailableCatalogs();
}
