package com.tecsup.app.micro.pedido.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsup.app.micro.pedido.application.usecase.CreateOrdertUseCase;
import com.tecsup.app.micro.pedido.application.usecase.GetAllOrdersUseCase;
import com.tecsup.app.micro.pedido.application.usecase.GetMaxOrdersUseCase;
import com.tecsup.app.micro.pedido.domain.model.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationService {

  private final CreateOrdertUseCase createOrderUseCase;
  private final GetAllOrdersUseCase getAllOrdersUseCase;
  private final GetMaxOrdersUseCase getMaxOrdersUseCase;

  @Transactional
  public Order createOrder(Order order, String token) {
    return createOrderUseCase.execute(order, token);
  }

  @Transactional(readOnly = true)
  public List<Order> getAllOrders() {
    return getAllOrdersUseCase.execute();
  }

  @Transactional(readOnly = true)
  public Long getMaxOrderNumber() {
    return getMaxOrdersUseCase.execute();
  }

}
