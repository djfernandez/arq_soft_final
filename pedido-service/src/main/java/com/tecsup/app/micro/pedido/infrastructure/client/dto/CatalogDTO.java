package com.tecsup.app.micro.pedido.infrastructure.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogDTO {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer stock;
  private String category;
  private Long createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private UserDTO createdByUser; // Relación con el usuario que creó el producto (opcional)
}
