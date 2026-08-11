package com.tecsup.app.micro.pedido.domain.repository;

import java.util.List;
import java.util.Optional;

import com.tecsup.app.micro.pedido.domain.model.Restaurant;

public interface RestaurantRepository {
  /**
   * Obtiene todas las órdenes
   */
  List<Restaurant> findAll();

  /**
   * Busca una pedido por ID
   */
  Optional<Restaurant> findById(Long id);

  /**
   * Busca órdenes por el ID del usuario
   */
  List<Restaurant> findByUserId(Long userId);

  /**
   * Busca órdenes disponibles (stock > 0)
   */
  List<Restaurant> findAvailableRestaurants();

  /**
   * Guarda una nueva pedido o actualiza una existente
   */
  Restaurant save(Restaurant restaurant);

  /**
   * Elimina una pedido por ID
   */
  void deleteById(Long id);

  /**
   * Verifica si existe una pedido con el ID dado
   */
  boolean existsById(Long id);

  /**
   * Obtiene el número de pedido máximo
   */
  Long maxOrderNumber();
}
