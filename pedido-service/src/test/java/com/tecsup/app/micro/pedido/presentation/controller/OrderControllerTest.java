package com.tecsup.app.micro.pedido.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tecsup.app.micro.pedido.application.service.OrderApplicationService;
import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.model.OrderItem;
import com.tecsup.app.micro.pedido.presentation.dto.CreateOrderItemRequest;
import com.tecsup.app.micro.pedido.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.pedido.presentation.mapper.OrderDtoMapper;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderApplicationService orderApplicationService;

    @Mock
    private OrderDtoMapper orderDtoMapper;

    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderApplicationService, orderDtoMapper);
    }

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
                .items(List.of(CreateOrderItemRequest.builder().catalogId(1L).quantity(2).build()))
                .build();

        Order domainOrder = Order.builder()
                .id(2L)
                .orderNumber("ORD-002")
                .userId(11L)
                .totalAmount(BigDecimal.valueOf(20.00))
                .status("PENDING")
                .items(List.of(OrderItem.builder().id(1L).catalogId(1L).quantity(2).build()))
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

    @Test
    void getAllOrdersShouldReturnEmptyListWhenNoOrders() {
        when(orderApplicationService.getAllOrders()).thenReturn(List.of());

        ResponseEntity<List<Order>> result = orderController.getAllOrders();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEmpty();
        verify(orderApplicationService).getAllOrders();
    }

    @Test
    void createOrderShouldThrowWhenServiceFails() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId(11L)
                .items(List.of(CreateOrderItemRequest.builder().catalogId(1L).quantity(2).build()))
                .build();

        Order domainOrder = Order.builder()
                .id(2L)
                .orderNumber("ORD-002")
                .userId(11L)
                .totalAmount(BigDecimal.valueOf(20.00))
                .status("PENDING")
                .items(List.of(OrderItem.builder().id(1L).catalogId(1L).quantity(2).build()))
                .build();

        when(orderDtoMapper.toDomain(request)).thenReturn(domainOrder);
        when(orderApplicationService.createOrder(domainOrder, "Bearer token"))
                .thenThrow(new RuntimeException("Service error"));

        assertThatThrownBy(() -> orderController.createOrder(request, "Bearer token"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Service error");
    }

    @Test
    void createOrderShouldMapDtoCorrectly() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId(12L)
                .items(List.of(
                        CreateOrderItemRequest.builder().catalogId(1L).quantity(3).build(),
                        CreateOrderItemRequest.builder().catalogId(2L).quantity(1).build()))
                .build();

        Order domainOrder = Order.builder()
                .id(3L)
                .orderNumber("ORD-003")
                .userId(12L)
                .totalAmount(BigDecimal.valueOf(50.00))
                .status("PENDING")
                .items(List.of(
                        OrderItem.builder().id(1L).catalogId(1L).quantity(3).build(),
                        OrderItem.builder().id(2L).catalogId(2L).quantity(1).build()))
                .build();

        when(orderDtoMapper.toDomain(request)).thenReturn(domainOrder);
        when(orderApplicationService.createOrder(domainOrder, "Bearer token"))
                .thenReturn(domainOrder);

        ResponseEntity<Order> result = orderController.createOrder(request, "Bearer token");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getUserId()).isEqualTo(12L);
        assertThat(result.getBody().getStatus()).isEqualTo("PENDING");
        assertThat(result.getBody().getItems()).hasSize(2);
        verify(orderDtoMapper).toDomain(request);
    }
}
