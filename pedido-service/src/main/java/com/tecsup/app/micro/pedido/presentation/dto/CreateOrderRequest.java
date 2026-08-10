package com.tecsup.app.micro.pedido.presentation.dto;

import java.util.List;

import com.tecsup.app.micro.pedido.domain.model.OrderItem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

  @NotNull(message = "UserId is required")
  @Positive(message = "UserId must be positive")
  private Long userId;

  @NotNull(message = "Items are required")
  private List<OrderItem> items;
}
