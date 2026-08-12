package com.tecsup.app.micro.pago.infrastructure.client.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  private Long id;
  private String orderNumber;
  private Long userId;
  private BigDecimal totalAmount;
  private String status;
}
