package com.tecsup.app.micro.pedido.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.domain.repository.RestaurantRepository;
import com.tecsup.app.micro.pedido.infrastructure.persistence.entity.RestaurantEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RestaurantRepositoryImpl implements RestaurantRepository {

  private final JpaRestaurantRepository jpaRestaurantRepository;

  @Override
  public List<Restaurant> findAll() {
    return jpaRestaurantRepository.findAll()
        .stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Restaurant> findById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    return jpaRestaurantRepository.findById(id)
        .map(this::toDomain);
  }

  @Override
  public List<Restaurant> findByUserId(Long userId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
  }

  @Override
  public List<Restaurant> findAvailableRestaurants() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAvailableRestaurants'");
  }

  @Override
  public Restaurant save(Restaurant restaurant) {
    log.debug("Saving restaurant: {}", restaurant);
    RestaurantEntity entity = toEntity(restaurant);
    RestaurantEntity savedEntity = jpaRestaurantRepository.save(entity);
    return toDomain(savedEntity);
  }

  @Override
  public void deleteById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

  @Override
  public boolean existsById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'existsById'");
  }

  @Override
  public Long maxOrderNumber() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'maxOrderNumber'");
  }

  private Restaurant toDomain(RestaurantEntity entity) {
    // Mapear los campos de RestaurantEntity a Restaurant
    return Restaurant.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .userId(entity.getUserId())
        .build();
  }

  private RestaurantEntity toEntity(Restaurant restaurant) {
    // Mapear los campos de Restaurant a RestaurantEntity
    RestaurantEntity entity = new RestaurantEntity();
    entity.setId(restaurant.getId());
    entity.setName(restaurant.getName());
    entity.setDescription(restaurant.getDescription());
    entity.setUserId(restaurant.getUserId());
    return entity;
  }

}
