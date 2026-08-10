package com.tecsup.app.micro.pedido.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.model.OrderItem;
import com.tecsup.app.micro.pedido.domain.repository.OrderRepository;
import com.tecsup.app.micro.pedido.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.pedido.infrastructure.persistence.entity.OrderItemEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;

  @Override
  public List<Order> findAll() {
    log.debug("Finding all orders");
    return jpaOrderRepository.findAll()
        .stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Order> findById(Long id) {
    log.debug("Finding order by id: {}", id);
    if (id == null) {
      return Optional.empty();
    }
    return jpaOrderRepository.findById(id)
        .map(this::toDomain);
  }

  @Override
  public List<Order> findByUserId(Long userId) {
    log.debug("Finding orders by user id: {}", userId);
    return jpaOrderRepository.findByUserId(userId)
        .stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Order> findAvailableOrders() {
    log.debug("Finding available orders");
    return jpaOrderRepository.findByStatus("AVAILABLE")
        .stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Order save(Order order) {
    log.debug("Saving order: {}", order);
    OrderEntity entity = toEntity(order);
    if (entity.getItems() != null) {
      entity.getItems().forEach(item -> item.setOrder(entity));
    }
    OrderEntity savedEntity = jpaOrderRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public void deleteById(Long id) {
    log.debug("Deleting order by id: {}", id);
    if (id != null) {
      jpaOrderRepository.deleteById(id);
    }
  }

  @Override
  public boolean existsById(Long id) {
    log.debug("Checking if order exists by id: {}", id);
    if (id == null) {
      return false;
    }
    return jpaOrderRepository.existsById(id);
  }

  @Override
  public Long maxOrderNumber() {
    return jpaOrderRepository.maxOrderNumber();
  }
  // Implementación del repositorio de pedido (Adaptador)
  // Conecta el dominio con la infraestructura de persistencia

  // Mappers

  private Order toDomain(OrderEntity entity) {
    return Order.builder()
        .id(entity.getId())
        .orderNumber(entity.getOrderNumber())
        .userId(entity.getUserId())
        .status(entity.getStatus())
        .totalAmount(entity.getTotalAmount())
        .items(entity.getItems()
            .stream()
            .map(item -> OrderItem.builder()
                .id(item.getId())
                .orderId(entity.getId())
                .catalogId(item.getCatalogId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal(item.getSubTotal())
                .build())
            .collect(Collectors.toList()))
        .build();
  }

  private OrderEntity toEntity(Order order) {
    return OrderEntity.builder()
        .orderNumber(order.getOrderNumber())
        .userId(order.getUserId())
        .status(order.getStatus())
        .totalAmount(order.getTotalAmount())
        .items(order.getItems()
            .stream()
            .map(item -> OrderItemEntity.builder()
                .catalogId(item.getCatalogId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subTotal(item.getSubtotal())
                .build())
            .collect(Collectors.toList()))
        .build();
  }

}
