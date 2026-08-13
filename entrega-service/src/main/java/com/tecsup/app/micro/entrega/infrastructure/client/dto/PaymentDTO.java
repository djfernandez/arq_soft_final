package com.tecsup.app.micro.entrega.infrastructure.client.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
  private Long id;
  private Long orderId;
  private Long userId;
  private BigDecimal amount;
  private String status; // PENDING, COMPLETED, FAILED, REFUNDED
}
