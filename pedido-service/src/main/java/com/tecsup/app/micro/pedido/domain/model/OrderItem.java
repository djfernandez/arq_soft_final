package com.tecsup.app.micro.pedido.domain.model;

import java.math.BigDecimal;

import com.tecsup.app.micro.pedido.application.dto.CatalogResumenDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

  private Long id;
  private Long orderId;
  private Long catalogId;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal subtotal;

  private CatalogResumenDTO product;

  public boolean isValid() {
    return catalogId != null && catalogId > 0
        && quantity != null && quantity > 0;
  }

}