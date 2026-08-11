package com.tecsup.app.micro.pedido.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecsup.app.micro.pedido.infrastructure.persistence.entity.RestaurantEntity;

public interface JpaRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
  // Define métodos de consulta personalizados si es necesario

}
