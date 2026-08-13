package com.tecsup.app.micro.entrega.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecsup.app.micro.entrega.domain.model.Order;
import com.tecsup.app.micro.entrega.infrastructure.client.dto.OrderDTO;
import com.tecsup.app.micro.entrega.infrastructure.client.mapper.OrderDtoMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClient {
  private final RestTemplate restTemplate;
  private final OrderDtoMapper orderDTOMapper;

  @Value("${order.service.url}")
  private String orderServiceUrl;

  /**
   * Obtiene un pedido por ID desde order-service
   *
   * @param orderId  ID del pedido a buscar
   * @param jwtToken Token JWT para autenticación (Sesión 2)
   * @return Order del dominio
   *
   *         Anotaciones Resilience4j (Sesión 3):
   * @CircuitBreaker: Si el 50% de las últimas 10 llamadas fallan,
   *                  abre el circuito por 10 segundos
   * @Retry: Reintenta hasta 3 veces con 1 segundo entre intentos
   */
  @CircuitBreaker(name = "entregaService")
  @Retry(name = "entregaService", fallbackMethod = "getOrderFallback")
  public Order getOrderById(Long orderId, String jwtToken) {
    log.info("Calling Order Service (PostgreSQL orderdb) to get order with id: {}", orderId);

    String url = this.orderServiceUrl + "/api/orders/" + orderId;

    // =============================================
    // Sesión 2: Propagar JWT en el header
    // =============================================
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    if (jwtToken != null && !jwtToken.isEmpty()) {
      headers.setBearerAuth(jwtToken);
    } else {
      log.warn("No JWT token provided for User Service call");
    }

    // Set JWT token in the Authorization header
    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {

      // UserDto user = restTemplate.getForObject(url, UserDto.class);
      // log.info("User retrieved successfully from userdb: {}", user);
      // return userDTOMapper.toDomain(user);

      ResponseEntity<OrderDTO> response = restTemplate.exchange(
          url, HttpMethod.GET, entity, OrderDTO.class);
      log.info("Order retrieved successfully from order-service: {}", response.getBody());
      return orderDTOMapper.toDomain(response.getBody());

    } catch (Exception e) {
      log.error("Error calling Order Service: {}", e.getMessage());
      throw new RuntimeException("Error calling Order Service: " + e.getMessage());
    }
  }

  /**
   * Metodo de versión anterior (sin JWT) - mantener para compatibilidad
   * Se puede eliminar una vez que JWT esté completamente implementado
   */
  public Order getOrderById(Long orderId) {
    return getOrderById(orderId, null);
  }

  /**
   * Fallback cuando order-service no está disponible (Sesión 3)
   *
   * Se ejecuta cuando:
   * - El Circuit Breaker está abierto
   * - Se agotaron los reintentos del Retry
   * - order-service no responde o retorna error
   *
   * @return Order con datos parciales (indica que el servicio no está disponible)
   */
  public Order getOrderFallback(Long orderId, String jwtToken, Throwable throwable) {
    log.warn("FALLBACK: Order Service no disponible para orderId: {}. Razón: {}",
        orderId, throwable.getMessage());

    return Order.builder()
        .id(orderId)
        .orderNumber("Orden no disponible")
        .status("N/A")
        .build();
  }
}
