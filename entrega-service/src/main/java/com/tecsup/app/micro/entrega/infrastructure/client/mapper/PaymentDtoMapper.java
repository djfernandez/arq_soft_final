package com.tecsup.app.micro.entrega.infrastructure.client.mapper;

import org.mapstruct.Mapper;

import com.tecsup.app.micro.entrega.domain.model.Payment;
import com.tecsup.app.micro.entrega.infrastructure.client.dto.PaymentDTO;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

  Payment toDomain(PaymentDTO dto);

}
