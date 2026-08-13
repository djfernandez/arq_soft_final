package com.tecsup.app.micro.pedido.application.usecase;

import org.springframework.stereotype.Service;

import com.tecsup.app.micro.pedido.domain.exception.InvalidOrderDataException;
import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.domain.model.User;
import com.tecsup.app.micro.pedido.domain.repository.RestaurantRepository;
import com.tecsup.app.micro.pedido.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateRestaurantsUseCase {

  private final RestaurantRepository restaurantRepository;
  private final UserClient userClient;

  public Restaurant execute(Restaurant restaurant, String token) {
    log.debug("Executing CreateRestaurantsUseCase for restaurant: {}", restaurant.getName());
    if (!restaurant.isValid()) {
      throw new IllegalArgumentException("Invalid restaurant data. Name, description, and userId are required.");
    }
    User user = userClient.getUserById(restaurant.getUserId(), token);
    if (user == null || user.getId() == null) {
      throw new InvalidOrderDataException("User with ID " + restaurant.getUserId() + " does not exist.");
    }
    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    log.info("Restaurant created successfully with id: {}", savedRestaurant.getId());

    return savedRestaurant;
  }
}
