package com.tecsup.app.micro.pago.infrastructure.persistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.infrastructure.persistence.entity.PaymentEntity;

/**
 * Mapper entre entidades JPA y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface PaymentPersistenceMapper {

    /**
     * Convierte ProductEntity a Product de dominio
     */
    Payment toDomain(PaymentEntity entity);

    /**
     * Convierte Product de dominio a ProductEntity
     */
    PaymentEntity toEntity(Payment product);

    /**
     * Convierte lista de ProductEntity a lista de Product
     */
    List<Payment> toDomainList(List<PaymentEntity> entities);
}
