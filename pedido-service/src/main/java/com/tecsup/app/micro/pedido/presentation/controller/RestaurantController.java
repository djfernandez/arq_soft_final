package com.tecsup.app.micro.pedido.presentation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.app.micro.pedido.application.service.RestaurantApplicationService;
import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.presentation.dto.CreateRestaurantRequest;
import com.tecsup.app.micro.pedido.presentation.mapper.RestaurantDtoMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

  private final RestaurantApplicationService restaurantApplicationService;
  private final RestaurantDtoMapper restaurantDtoMapper;

  /**
   * Obtiene todas las órdenes (solo ADMIN)
   */
  @GetMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<Restaurant>> getAllRestaurants() {
    log.info("REST request to get all restaurants");
    List<Restaurant> restaurants = restaurantApplicationService.getAllRestaurants();
    return ResponseEntity.ok(restaurants);
  }

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Restaurant> createRestaurant(
      @RequestBody CreateRestaurantRequest createRestaurantRequest,
      @RequestHeader("Authorization") String authorizationHeader) {
    log.info("REST request to create restaurant: {}", createRestaurantRequest);
    log.info("Authorization header: {}", authorizationHeader);
    Restaurant restaurantDomain = restaurantDtoMapper.toDomain(createRestaurantRequest);
    Restaurant createdRestaurant = restaurantApplicationService.createRestaurant(restaurantDomain, authorizationHeader);
    return ResponseEntity.ok(createdRestaurant);
  }

  /**
   * Endpoint de salud (público, sin autenticación)
   */
  @GetMapping("/health")
  public ResponseEntity<String> health() {
    return ResponseEntity.ok("Restaurant Service running with Clean Architecture!");
  }
}
