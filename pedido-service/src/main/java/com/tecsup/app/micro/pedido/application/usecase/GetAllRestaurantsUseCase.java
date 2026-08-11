package com.tecsup.app.micro.pedido.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.domain.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllRestaurantsUseCase {

  private final RestaurantRepository restaurantRepository;

  public List<Restaurant> execute() {
    return restaurantRepository.findAll();
  }

}
