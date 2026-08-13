package com.tecsup.app.micro.entrega.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.entrega.domain.model.Delivery;
import com.tecsup.app.micro.entrega.infrastructure.persistence.entity.DeliveryEntity;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface DeliveryPersistenceMapper {

    /**
     * Convierte ProductEntity a Product de dominio
     */
    @Mapping(target = "createdByUser", ignore = true)
    Delivery toDomain(DeliveryEntity entity);

    /**
     * Convierte Product de dominio a ProductEntity
     */
    @Mapping(target = "deliveredAt", ignore = true)
    DeliveryEntity toEntity(Delivery product);

    /**
     * Convierte lista de ProductEntity a lista de Product
     */
    List<Delivery> toDomainList(List<DeliveryEntity> entities);
}
