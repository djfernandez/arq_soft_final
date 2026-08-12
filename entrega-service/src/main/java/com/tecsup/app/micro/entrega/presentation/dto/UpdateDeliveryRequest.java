package com.tecsup.app.micro.entrega.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para actualizar un producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDeliveryRequest {

    @NotBlank(message = "ID is required")
    private Long id;

    @NotBlank(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Order ID is required")
    private Long orderId;

    @NotBlank(message = "Payment ID is required")
    private Long paymentId;

    @NotBlank(message = "Delivery ID is required")
    private Long deliveryId;

    @NotBlank(message = "Status is required")
    private String status;

}
