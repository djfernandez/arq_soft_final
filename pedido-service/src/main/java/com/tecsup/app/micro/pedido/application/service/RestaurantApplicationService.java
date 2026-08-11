package com.tecsup.app.micro.pedido.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsup.app.micro.pedido.application.usecase.CreateRestaurantsUseCase;
import com.tecsup.app.micro.pedido.application.usecase.GetAllRestaurantsUseCase;
import com.tecsup.app.micro.pedido.domain.model.Restaurant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantApplicationService {

  private final GetAllRestaurantsUseCase getAllRestaurantsUseCase;
  private final CreateRestaurantsUseCase createRestaurantsUseCase;

  @Transactional(readOnly = true)
  public List<Restaurant> getAllRestaurants() {
    return getAllRestaurantsUseCase.execute();
  }

  @Transactional
  public Restaurant createRestaurant(Restaurant restaurant, String token) {
    return createRestaurantsUseCase.execute(restaurant, token);
  }

}
