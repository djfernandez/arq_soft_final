package com.tecsup.app.micro.pedido.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllOrdersUseCase {
  private final OrderRepository orderRepository;

  public List<Order> execute() {
    return orderRepository.findAll();
  }
}
