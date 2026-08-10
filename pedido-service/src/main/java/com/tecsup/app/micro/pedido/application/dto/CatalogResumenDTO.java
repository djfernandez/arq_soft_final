package com.tecsup.app.micro.pedido.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResumenDTO {
  private Long id;
  private String name;
  private BigDecimal price;

}
