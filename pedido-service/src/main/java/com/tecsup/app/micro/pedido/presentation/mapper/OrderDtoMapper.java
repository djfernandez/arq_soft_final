package com.tecsup.app.micro.pedido.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.presentation.dto.CreateOrderRequest;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "status", ignore = true)
  @Mapping(target = "totalAmount", ignore = true)
  @Mapping(target = "restaurantId", source = "restaurantId")
  Order toDomain(CreateOrderRequest createOrderRequest);

}
