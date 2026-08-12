package com.tecsup.app.micro.entrega.presentation.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta de producto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private Long deliveryId;
    private String status;
    private LocalDateTime deliveredAt;

}
