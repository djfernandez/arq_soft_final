package com.tecsup.app.micro.pedido.application.usecase;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pedido.application.dto.CatalogResumenDTO;
import com.tecsup.app.micro.pedido.domain.exception.InvalidOrderDataException;
import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.repository.OrderRepository;
import com.tecsup.app.micro.pedido.infrastructure.client.CatalogClient;
import com.tecsup.app.micro.pedido.infrastructure.client.UserClient;
import com.tecsup.app.micro.pedido.infrastructure.client.dto.CatalogDTO;
import com.tecsup.app.micro.pedido.infrastructure.client.dto.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrdertUseCase {

  private final OrderRepository orderRepository;
  private final UserClient userClient;
  private final CatalogClient productClient;

  public Order execute(Order order, String token) {
    // Validar datos de la pedido
    if (!order.isValid()) {
      throw new InvalidOrderDataException("Invalid order data. Customer name and valid total amount are required.");
    }

    if (order.getItems().stream().noneMatch(item -> item.isValid())) {
      throw new InvalidOrderDataException("Invalid order items. At least one valid item is required.");
    }

    UserDTO userDTO = userClient.getUserById(order.getUserId(), token);
    if (userDTO == null || userDTO.getId() == null) {
      throw new InvalidOrderDataException("User with ID " + order.getUserId() + " does not exist.");
    }

    List<Long> productIds = order.getItems().stream()
        .map(item -> item.getCatalogId())
        .toList();

    List<CatalogDTO> products = productClient.getProductById(productIds, token);
    if (products.size() != productIds.size()) {
      throw new InvalidOrderDataException("Some products in the order do not exist.");
    }

    // Asociar productos a los items de la pedido
    order.getItems().forEach(item -> {
      products.stream()
          .filter(product -> product.getId().equals(item.getCatalogId()))
          .findFirst()
          .ifPresent(product -> {
            CatalogResumenDTO productResumen = CatalogResumenDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
            item.setProduct(productResumen);
          });
    });
    log.info("Order items associated with products successfully for user: {}", userDTO.getName());

    order.getItems().forEach(item -> {
      item.setSubtotal(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
      item.setUnitPrice(item.getProduct().getPrice());
    });

    log.info("Order items subtotal and unit price calculated successfully for user: {}", userDTO.getName());

    BigDecimal totalAmount = order.getItems().stream()
        .map(item -> item.getSubtotal())
        .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    order.setTotalAmount(totalAmount);

    log.info("Order total amount calculated successfully for user: {}", userDTO.getName());

    order.setStatus("PENDING");
    order.setOrderNumber(null);

    Long maxOrderNumber = orderRepository.maxOrderNumber();
    String orderNumber = (maxOrderNumber != null) ? String.valueOf(maxOrderNumber + 1) : "1";
    order.setOrderNumber("ORD-" + Year.now().getValue() + "-00" + orderNumber);
    log.info("Order number generated successfully for user: {}", userDTO.getName());

    log.info("Order validated successfully for user: {}", userDTO.getName());
    // Guardar pedido
    Order savedOrder = orderRepository.save(order);

    // Asociar productos a los items de la pedido
    savedOrder.getItems().forEach(item -> {
      products.stream()
          .filter(product -> product.getId().equals(item.getCatalogId()))
          .findFirst()
          .ifPresent(product -> {
            CatalogResumenDTO productResumen = CatalogResumenDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
            item.setProduct(productResumen);
          });
    });

    return savedOrder;
  }

}