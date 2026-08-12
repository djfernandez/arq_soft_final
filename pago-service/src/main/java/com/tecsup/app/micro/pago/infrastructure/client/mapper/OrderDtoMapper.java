package com.tecsup.app.micro.pago.infrastructure.client.mapper;

import org.mapstruct.Mapper;

import com.tecsup.app.micro.pago.domain.model.Order;
import com.tecsup.app.micro.pago.infrastructure.client.dto.OrderDTO;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface OrderDtoMapper {

  Order toDomain(OrderDTO dto);

  // OrderResponse toResponse(Order order);

}