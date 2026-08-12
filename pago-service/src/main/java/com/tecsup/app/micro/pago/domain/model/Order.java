package com.tecsup.app.micro.pago.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payment Domain Model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private Long id;
  private String orderNumber;
  private Long userId;
  private BigDecimal totalAmount;
  private String status;
}
