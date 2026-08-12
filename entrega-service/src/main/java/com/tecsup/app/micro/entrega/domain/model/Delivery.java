package com.tecsup.app.micro.entrega.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Domain Model (Core Business Entity)
 * Esta es la entidad de dominio pura, sin dependencias de frameworks
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    private Long id;
    private Long userId;
    private Long orderId;
    private Long paymentId;
    private Long deliveryId;
    private String status;
    private LocalDateTime deliveredAt;

    private User createdByUser; // Relación con el usuario que creó el producto (opcional)

    /**
     * Valida que el producto tenga los datos mínimos requeridos
     */
    public boolean isValid() {
        return userId != null && userId > 0
                && orderId != null && orderId > 0
                && paymentId != null && paymentId > 0
                && status != null && !status.isEmpty();
    }

}
