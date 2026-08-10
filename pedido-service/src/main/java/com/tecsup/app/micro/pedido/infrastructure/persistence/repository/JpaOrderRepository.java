package com.tecsup.app.micro.pedido.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import com.tecsup.app.micro.pedido.infrastructure.persistence.entity.OrderEntity;

public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
  // Define métodos de consulta personalizados si es necesario

  Optional<OrderEntity> findByUserId(Long userId);

  Optional<OrderEntity> findByCreatedAt(LocalDateTime createdAt);

  Optional<OrderEntity> findByStatus(String status);

  @Query("SELECT MAX(o.id) FROM OrderEntity o")
  Long maxOrderNumber();
}
