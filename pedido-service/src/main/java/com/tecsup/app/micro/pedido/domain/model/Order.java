package com.tecsup.app.micro.pedido.domain.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  // private LocalDateTime createdAt;
  // private LocalDateTime updatedAt;
  private List<OrderItem> items;

  public boolean isValid() {
    return userId != null && userId > 0
        && items != null && !items.isEmpty();
  }

}
