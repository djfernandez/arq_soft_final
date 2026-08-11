package com.tecsup.app.micro.pedido.presentation.controller;

import com.tecsup.app.micro.pedido.application.service.OrderApplicationService;
import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.model.OrderItem;
import com.tecsup.app.micro.pedido.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.pedido.presentation.mapper.OrderDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {

  private final OrderApplicationService orderApplicationService = mock(OrderApplicationService.class);
  private final OrderDtoMapper orderDtoMapper = mock(OrderDtoMapper.class);
  private final OrderController orderController = new OrderController(orderApplicationService, orderDtoMapper);

  @Test
  void getAllOrdersShouldReturnListOfOrders() {
    Order order = Order.builder()
        .id(1L)
        .orderNumber("ORD-001")
        .userId(10L)
        .totalAmount(BigDecimal.valueOf(25.50))
        .status("PENDING")
        .build();

    when(orderApplicationService.getAllOrders()).thenReturn(List.of(order));

    ResponseEntity<List<Order>> result = orderController.getAllOrders();

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).hasSize(1);
    assertThat(result.getBody().get(0).getOrderNumber()).isEqualTo("ORD-001");
    verify(orderApplicationService).getAllOrders();
  }

  @Test
  void createOrderShouldReturnCreatedOrder() {
    CreateOrderRequest request = CreateOrderRequest.builder()
        .userId(11L)
        .items(List.of(OrderItem.builder().catalogId(1L).quantity(2).unitPrice(BigDecimal.TEN).build()))
        .build();

    Order domainOrder = Order.builder()
        .id(2L)
        .orderNumber("ORD-002")
        .userId(11L)
        .totalAmount(BigDecimal.valueOf(20.00))
        .status("PENDING")
        .items(request.getItems())
        .build();

    when(orderDtoMapper.toDomain(request)).thenReturn(domainOrder);
    when(orderApplicationService.createOrder(domainOrder, "Bearer token"))
        .thenReturn(domainOrder);

    ResponseEntity<Order> result = orderController.createOrder(request, "Bearer token");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isNotNull();
    assertThat(result.getBody().getOrderNumber()).isEqualTo("ORD-002");
    verify(orderDtoMapper).toDomain(request);
    verify(orderApplicationService).createOrder(domainOrder, "Bearer token");
  }

  @Test
  void healthShouldReturnServiceMessage() {
    ResponseEntity<String> result = orderController.health();

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).isEqualTo("Order Service running with Clean Architecture!");
  }
}
