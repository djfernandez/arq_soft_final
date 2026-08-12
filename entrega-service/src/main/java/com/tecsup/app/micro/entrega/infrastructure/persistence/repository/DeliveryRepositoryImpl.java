package com.tecsup.app.micro.entrega.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.domain.repository.DeliveryRepository;
import com.tecsup.app.micro.entrega.infrastructure.persistence.entity.DeliveryEntity;
import com.tecsup.app.micro.entrega.infrastructure.persistence.mapper.DeliveryPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del repositorio de Producto (Adaptador)
 * Conecta el dominio con la infraestructura de persistencia usando MapStruct
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class DeliveryRepositoryImpl implements DeliveryRepository {

  private final JpaDeliveryRepository jpaProductRepository;
  private final DeliveryPersistenceMapper mapper;

  @Override
  public List<Delivery> findAll() {
    log.debug("Finding all deliveries");
    return mapper.toDomainList((List<DeliveryEntity>) jpaProductRepository.findAll());
  }

  @Override
  public Optional<Delivery> findById(Long id) {
    log.debug("Finding delivery by id: {}", id);
    return jpaProductRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public List<Delivery> findByUserId(Long userId) {
    log.debug("Finding deliveries by userId: {}", userId);
    return mapper.toDomainList(jpaProductRepository.findByUserId(userId));
  }

  @Override
  public Delivery save(Delivery product) {
    log.debug("Saving delivery: {}", product.getId());
    DeliveryEntity entity = mapper.toEntity(product);
    DeliveryEntity savedEntity = jpaProductRepository.save(entity);
    return mapper.toDomain(savedEntity);
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting delivery by id: {}", id);
    jpaProductRepository.deleteById(id);
  }

  @Override
  public boolean existsById(Long id) {
    log.debug("Checking if delivery exists: {}", id);
    return jpaProductRepository.existsById(id);
  }

}
