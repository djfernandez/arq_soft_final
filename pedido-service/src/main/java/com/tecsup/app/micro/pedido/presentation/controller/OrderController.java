package com.tecsup.app.micro.pedido.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.app.micro.pedido.application.service.OrderApplicationService;
import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.pedido.presentation.mapper.OrderDtoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final OrderApplicationService orderApplicationService;
  private final OrderDtoMapper orderDtoMapper;

  /**
   * Obtiene todas las órdenes (solo ADMIN)
   */
  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Order>> getAllOrders() {
    log.info("REST request to get all orders");
    List<Order> orders = orderApplicationService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{id}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    log.info("REST request to get order by id: {}", id);
    Order order = orderApplicationService.getOrderById(id);
    return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Order> createOrder(
      @RequestBody CreateOrderRequest order,
      @RequestHeader("Authorization") String authHeader) {
    log.info("REST request to create order: {}", order);
    log.info("Authorization header: {}", authHeader);

    // Extraer JWT del header
    String jwtToken = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      jwtToken = authHeader.substring(7);
    } else {
      log.warn("No Authorization header with Bearer token found for catalog retrieval");
    }
    Order orderDomain = orderDtoMapper.toDomain(order);
    Order createdOrder = orderApplicationService.createOrder(orderDomain, jwtToken);
    return ResponseEntity.ok(createdOrder);
  }

  /**
   * Endpoint de salud (público, sin autenticación)
   */
  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Order Service running with Clean Architecture!");
  }
}
