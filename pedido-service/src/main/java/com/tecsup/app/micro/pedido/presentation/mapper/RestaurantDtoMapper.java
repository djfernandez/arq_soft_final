package com.tecsup.app.micro.pedido.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.pedido.domain.model.Restaurant;
import com.tecsup.app.micro.pedido.presentation.dto.CreateRestaurantRequest;

@Mapper(componentModel = "spring")
public interface RestaurantDtoMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orders", ignore = true)
  Restaurant toDomain(CreateRestaurantRequest createRestaurantRequest);

}
