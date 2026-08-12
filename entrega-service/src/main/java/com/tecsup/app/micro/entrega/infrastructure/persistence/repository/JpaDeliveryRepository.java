package com.tecsup.app.micro.entrega.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tecsup.app.micro.entrega.infrastructure.persistence.entity.DeliveryEntity;

/**
 * Repositorio JPA de Producto
 * Interface de Spring Data JPA para operaciones de persistencia
 */
public interface JpaDeliveryRepository extends CrudRepository<DeliveryEntity, Long> {

    List<DeliveryEntity> findByUserId(Long userId);

}
