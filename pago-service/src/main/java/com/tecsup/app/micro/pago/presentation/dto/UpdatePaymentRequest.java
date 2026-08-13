package com.tecsup.app.micro.pago.presentation.dto;

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
public class UpdatePaymentRequest {

    @NotBlank(message = "Name is required")
    private Long id;

    @NotBlank(message = "Name is required")
    private Long userId;

    @NotBlank(message = "Name is required")
    private Long orderId;

}
