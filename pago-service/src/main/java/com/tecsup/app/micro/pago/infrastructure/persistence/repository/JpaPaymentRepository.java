package com.tecsup.app.micro.pago.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecsup.app.micro.pago.infrastructure.persistence.entity.PaymentEntity;

/**
 * Repositorio JPA de Producto
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaPaymentRepository extends CrudRepository<PaymentEntity, Long> {

    List<PaymentEntity> findByUserId(Long userId);

    List<PaymentEntity> findByIdIn(List<Long> ids);
}
