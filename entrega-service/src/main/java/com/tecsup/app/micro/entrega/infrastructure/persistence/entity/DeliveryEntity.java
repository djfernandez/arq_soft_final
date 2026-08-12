package com.tecsup.app.micro.entrega.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA de Producto
 * Esta clase pertenece a la capa de infraestructura y maneja la persistencia
 */
@Entity
@Table(name = "deliveries", indexes = {
        @Index(name = "idx_deliveries_category", columnList = "category"),
        @Index(name = "idx_deliveries_created_by", columnList = "created_by"),
        @Index(name = "idx_deliveries_price", columnList = "price"),
        @Index(name = "idx_deliveries_stock", columnList = "stock"),
        @Index(name = "idx_deliveries_created_at", columnList = "created_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "delivery_id", nullable = false)
    private Long deliveryId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        deliveredAt = now;
    }
}
