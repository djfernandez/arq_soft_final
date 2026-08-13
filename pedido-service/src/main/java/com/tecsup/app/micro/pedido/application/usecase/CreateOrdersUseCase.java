package com.tecsup.app.micro.pedido.application.usecase;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pedido.application.dto.CatalogResumenDTO;
import com.tecsup.app.micro.pedido.domain.exception.InvalidOrderDataException;
import com.tecsup.app.micro.pedido.domain.model.Order;
import com.tecsup.app.micro.pedido.domain.model.User;
import com.tecsup.app.micro.pedido.domain.repository.OrderRepository;
import com.tecsup.app.micro.pedido.infrastructure.client.CatalogClient;
import com.tecsup.app.micro.pedido.infrastructure.client.UserClient;
import com.tecsup.app.micro.pedido.infrastructure.client.dto.CatalogDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrdersUseCase {

  private final OrderRepository orderRepository;
  private final UserClient userClient;
  private final CatalogClient productClient;

  public Order execute(Order order, String token) {
    // Validar datos de la pedido
    if (!order.isValid()) {
      throw new InvalidOrderDataException("Invalid order data. Customer name and valid total amount are required.");
    }

    log.info(order.toString());

    if (order.getItems().stream().noneMatch(item -> item.isValid())) {
      throw new InvalidOrderDataException("Invalid order items. At least one valid item is required.");
    }

    User user = userClient.getUserById(order.getUserId(), token);
    if (user == null || user.getId() == null) {
      throw new InvalidOrderDataException("User with ID " + order.getUserId() + " does not exist.");
    }

    List<Long> catalogIds = order.getItems().stream()
        .map(item -> item.getCatalogId())
        .toList();

    List<CatalogDTO> catalogItems = productClient.getCatalogsByIds(catalogIds, token);
    if (catalogItems.size() != catalogIds.size()) {
      throw new InvalidOrderDataException("Some products in the order do not exist.");
    }

    // Asociar productos a los items de la pedido
    order.getItems().forEach(item -> {
      catalogItems.stream()
          .filter(catalog -> catalog.getId().equals(item.getCatalogId()))
          .findFirst()
          .ifPresent(catalog -> {
            CatalogResumenDTO catalogResumen = CatalogResumenDTO.builder()
                .id(catalog.getId())
                .name(catalog.getName())
                .price(catalog.getPrice())
                .build();
            item.setProduct(catalogResumen);
          });
    });
    log.info("Order items associated with products successfully for user: {}", user.getName());

    order.getItems().forEach(item -> {
      item.setSubtotal(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
      item.setUnitPrice(item.getProduct().getPrice());
    });

    log.info("Order items subtotal and unit price calculated successfully for user: {}", user.getName());

    BigDecimal totalAmount = order.getItems().stream()
        .map(item -> item.getSubtotal())
        .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    order.setTotalAmount(totalAmount);

    log.info("Order total amount calculated successfully for user: {}", user.getName());

    order.setStatus("PENDING");
    order.setOrderNumber(null);

    Long maxOrderNumber = orderRepository.maxOrderNumber();
    String orderNumber = (maxOrderNumber != null) ? String.valueOf(maxOrderNumber + 1) : "1";
    order.setOrderNumber("ORD-" + Year.now().getValue() + "-00" + orderNumber);
    log.info("Order number generated successfully for user: {}", user.getName());

    log.info("Order validated successfully for user: {}", user.getName());
    // Guardar pedido
    Order savedOrder = orderRepository.save(order);

    // Asociar productos a los items de la pedido
    savedOrder.getItems().forEach(item -> {
      catalogItems.stream()
          .filter(catalog -> catalog.getId().equals(item.getCatalogId()))
          .findFirst()
          .ifPresent(catalog -> {
            CatalogResumenDTO catalogResumen = CatalogResumenDTO.builder()
                .id(catalog.getId())
                .name(catalog.getName())
                .price(catalog.getPrice())
                .build();
            item.setProduct(catalogResumen);
          });
    });

    return savedOrder;
  }

}