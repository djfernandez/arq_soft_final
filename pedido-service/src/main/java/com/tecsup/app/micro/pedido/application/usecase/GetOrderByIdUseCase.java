package com.tecsup.app.micro.pedido.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetOrderByIdUseCase {
  private final OrderRepository orderRepository;

  public Order execute(Long id) {
    return orderRepository.findById(id).orElse(null);
  }
}
