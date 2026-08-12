package com.tecsup.app.micro.pago.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;
import com.tecsup.app.micro.pago.infrastructure.persistence.entity.PaymentEntity;
import com.tecsup.app.micro.pago.infrastructure.persistence.mapper.PaymentPersistenceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementación del repositorio de Producto (Adaptador)
 * Conecta el dominio con la infraestructura de persistencia usando MapStruct
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

  private final JpaPaymentRepository jpaProductRepository;
  private final PaymentPersistenceMapper mapper;

  @Override
  public List<Payment> findAll() {
    log.debug("Finding all products");
    return mapper.toDomainList((List<PaymentEntity>) jpaProductRepository.findAll());
  }

  @Override
  public Optional<Payment> findById(Long id) {
    log.debug("Finding product by id: {}", id);
    return jpaProductRepository.findById(id)
        .map(mapper::toDomain);
  }

  @Override
  public List<Payment> findByUserId(Long userId) {
    log.debug("Finding products by userId: {}", userId);
    return mapper.toDomainList(jpaProductRepository.findByUserId(userId));
  }

  @Override
  public Payment save(Payment product) {
    log.debug("Saving product: {}", product.getId());
    PaymentEntity entity = mapper.toEntity(product);
    PaymentEntity savedEntity = jpaProductRepository.save(entity);
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
  public List<Payment> findAllById(List<Long> ids) {
    log.debug("Finding products by ids: {}", ids);
    return mapper.toDomainList(jpaProductRepository.findByIdIn(ids));
  }
}
