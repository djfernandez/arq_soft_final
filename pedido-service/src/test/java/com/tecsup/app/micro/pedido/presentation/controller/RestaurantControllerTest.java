package com.tecsup.app.micro.pedido.presentation.controller;

import com.tecsup.app.micro.pedido.application.service.RestaurantApplicationService;
import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.presentation.dto.CreateRestaurantRequest;
import com.tecsup.app.micro.pedido.presentation.mapper.RestaurantDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

  private final RestaurantApplicationService restaurantApplicationService = mock(RestaurantApplicationService.class);
  private final RestaurantDtoMapper restaurantDtoMapper = mock(RestaurantDtoMapper.class);
  private final RestaurantController restaurantController = new RestaurantController(
      restaurantApplicationService,
      restaurantDtoMapper);

  @Test
  void getAllRestaurantsShouldReturnListOfRestaurants() {
    Restaurant restaurant = Restaurant.builder()
        .id(1L)
        .name("La Casa del Sabor")
        .description("Restaurante tradicional")
        .userId(10L)
        .build();

    when(restaurantApplicationService.getAllRestaurants()).thenReturn(List.of(restaurant));

    ResponseEntity<List<Restaurant>> result = restaurantController.getAllRestaurants();

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).hasSize(1);
    assertThat(result.getBody().get(0).getName()).isEqualTo("La Casa del Sabor");
    verify(restaurantApplicationService).getAllRestaurants();
  }

  @Test
  void createRestaurantShouldReturnCreatedRestaurant() {
    CreateRestaurantRequest request = CreateRestaurantRequest.builder()
        .name("El Buen Sabor")
        .description("Comida casera")
        .userId(11L)
        .build();

    Restaurant domainRestaurant = Restaurant.builder()
        .id(2L)
        .name("El Buen Sabor")
        .description("Comida casera")
        .userId(11L)
        .build();

    when(restaurantDtoMapper.toDomain(request)).thenReturn(domainRestaurant);
    when(restaurantApplicationService.createRestaurant(domainRestaurant, "Bearer token"))
        .thenReturn(domainRestaurant);

    ResponseEntity<Restaurant> result = restaurantController.createRestaurant(request, "Bearer token");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getName()).isEqualTo("El Buen Sabor");
    verify(restaurantDtoMapper).toDomain(request);
    verify(restaurantApplicationService).createRestaurant(domainRestaurant, "Bearer token");
  }

  @Test
  void healthShouldReturnServiceMessage() {
    ResponseEntity<String> result = restaurantController.health();

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isEqualTo("Restaurant Service running with Clean Architecture!");
  }
}
