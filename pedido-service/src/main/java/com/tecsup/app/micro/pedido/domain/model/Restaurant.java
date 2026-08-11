package com.tecsup.app.micro.pedido.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
  private Long id;
  private String name;
  private String description;
  private Long userId;

  private List<Order> orders;

  public boolean isValid() {
    return id != null && id > 0
        && name != null && !name.isEmpty()
        && description != null && !description.isEmpty()
        && userId != null && userId > 0;
  }

}
