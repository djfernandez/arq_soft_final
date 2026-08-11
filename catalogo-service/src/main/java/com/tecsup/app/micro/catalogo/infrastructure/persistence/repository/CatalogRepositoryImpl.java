package com.tecsup.app.micro.catalogo.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tecsup.app.micro.catalogo.domain.model.Catalog;
import com.tecsup.app.micro.catalogo.domain.repository.CatalogRepository;
import com.tecsup.app.micro.catalogo.infrastructure.persistence.entity.CatalogEntity;
import com.tecsup.app.micro.catalogo.infrastructure.persistence.mapper.CatalogPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del repositorio de Producto (Adaptador)
 * Conecta el dominio con la infraestructura de persistencia usando MapStruct
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class CatalogRepositoryImpl implements CatalogRepository {

  private final JpaCatalogRepository jpaProductRepository;
  private final CatalogPersistenceMapper mapper;

  @Override
  public List<Catalog> findAll() {
    log.debug("Finding all products");
    return mapper.toDomainList((List<CatalogEntity>) jpaProductRepository.findAll());
  }

  @Override
  public Optional<Catalog> findById(Long id) {
    log.debug("Finding product by id: {}", id);
    return jpaProductRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public List<Catalog> findByCategory(String category) {
    log.debug("Finding products by category: {}", category);
    return mapper.toDomainList(jpaProductRepository.findByCategory(category));
  }

  @Override
  public List<Catalog> findByCreatedBy(Long userId) {
    log.debug("Finding products by createdBy: {}", userId);
    return mapper.toDomainList(jpaProductRepository.findByCreatedBy(userId));
  }

  @Override
  public List<Catalog> findAvailableCatalogs() {
    log.debug("Finding available catalogs");
    return mapper.toDomainList(jpaProductRepository.findAvailableCatalogs());
  }

  @Override
  public Catalog save(Catalog product) {
    log.debug("Saving product: {}", product.getName());
    CatalogEntity entity = mapper.toEntity(product);
    CatalogEntity savedEntity = jpaProductRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting product by id: {}", id);
    jpaProductRepository.deleteById(id);
  }

  @Override
  public boolean existsById(Long id) {
    log.debug("Checking if product exists: {}", id);
    return jpaProductRepository.existsById(id);
  }

  @Override
  public List<Catalog> findAllById(List<Long> ids) {
    log.debug("Finding products by ids: {}", ids);
    return mapper.toDomainList(jpaProductRepository.findByIdIn(ids));
  }
}
