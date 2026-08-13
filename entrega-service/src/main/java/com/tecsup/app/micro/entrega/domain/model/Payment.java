package com.tecsup.app.micro.entrega.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
  private Long id;
  private Long orderId;
  private Long userId;
  private BigDecimal amount;
  private String status; // PENDING, COMPLETED, FAILED, REFUNDED
  private LocalDateTime paidAt;
}
