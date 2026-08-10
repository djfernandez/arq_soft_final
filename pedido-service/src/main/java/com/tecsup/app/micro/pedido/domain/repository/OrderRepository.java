package com.tecsup.app.micro.pedido.domain.repository;

import java.util.List;
import java.util.Optional;

import com.tecsup.app.micro.pedido.domain.model.Order;

public interface OrderRepository {

  /**
   * Obtiene todas las órdenes
   */
  List<Order> findAll();

  /**
   * Busca una pedido por ID
   */
  Optional<Order> findById(Long id);

  /**
   * Busca órdenes por el ID del usuario
   */
  List<Order> findByUserId(Long userId);

  /**
   * Busca órdenes disponibles (stock > 0)
   */
  List<Order> findAvailableOrders();

  /**
   * Guarda una nueva pedido o actualiza una existente
   */
  Order save(Order order);

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
