package com.tecsup.app.micro.entrega.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.presentation.dto.CreateDeliveryRequest;
import com.tecsup.app.micro.entrega.presentation.dto.DeliveryResponse;
import com.tecsup.app.micro.entrega.presentation.dto.UpdateDeliveryRequest;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface DeliveryDtoMapper {

    /**
     * Convierte CreateDeliveryRequest a Delivery de dominio
     */
    Delivery toDomain(CreateDeliveryRequest request);

    /**
     * Convierte UpdateDeliveryRequest a Delivery de dominio
     */

    Delivery toDomain(UpdateDeliveryRequest request);

    /**
     * Convierte Delivery de dominio a DeliveryResponse
     */
    DeliveryResponse toResponse(Delivery delivery);

    /**
     * Convierte lista de Deliveries a lista de DeliveryResponse
     */
    List<DeliveryResponse> toResponseList(List<Delivery> delivery);
}
