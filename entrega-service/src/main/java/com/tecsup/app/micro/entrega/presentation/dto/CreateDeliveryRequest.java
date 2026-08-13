package com.tecsup.app.micro.entrega.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear un catálogo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "User ID is required")
    private Long orderId;

    @NotNull(message = "Payment ID is required")
    private Long paymentId;

    @NotNull(message = "Delivery ID is required")
    private Long deliveryId;

    @NotBlank(message = "Status is required")
    private String status;

}
