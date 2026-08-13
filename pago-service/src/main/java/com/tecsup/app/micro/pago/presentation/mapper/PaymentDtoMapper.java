package com.tecsup.app.micro.pago.presentation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.presentation.dto.CreatePaymentRequest;
import com.tecsup.app.micro.pago.presentation.dto.PaymentResponse;
import com.tecsup.app.micro.pago.presentation.dto.UpdatePaymentRequest;

/**
 * Mapper entre DTOs de presentación y modelo de dominio usando MapStruct
 */
@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

    /**
     * Convierte CreatePaymentRequest a Payment de dominio
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Payment toDomain(CreatePaymentRequest request);

    /**
     * Convierte UpdatePaymentRequest a Payment de dominio
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    Payment toDomain(UpdatePaymentRequest request);

    /**
     * Convierte Payment de dominio a PaymentResponse
     */
    PaymentResponse toResponse(Payment payment);

    /**
     * Convierte lista de Payments a lista de PaymentResponse
     */
    List<PaymentResponse> toResponseList(List<Payment> payments);
}
