package com.tecsup.app.micro.entrega.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecsup.app.micro.entrega.domain.model.Payment;
import com.tecsup.app.micro.entrega.infrastructure.client.dto.PaymentDTO;
import com.tecsup.app.micro.entrega.infrastructure.client.mapper.PaymentDtoMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentClient {
  private final RestTemplate restTemplate;
  private final PaymentDtoMapper paymentDTOMapper;

  @Value("${payment.service.url}")
  private String paymentServiceUrl;

  /**
   * Obtiene un pago por ID desde payment-service
   *
   * @param paymentId ID del pago a buscar
   * @param jwtToken  Token JWT para autenticación (Sesión 2)
   * @return Order del dominio
   *
   *         Anotaciones Resilience4j (Sesión 3):
   * @CircuitBreaker: Si el 50% de las últimas 10 llamadas fallan,
   *                  abre el circuito por 10 segundos
   * @Retry: Reintenta hasta 3 veces con 1 segundo entre intentos
   */
  @CircuitBreaker(name = "entregaService")
  @Retry(name = "entregaService", fallbackMethod = "getOrderFallback")
  public Payment getPaymentById(Long paymentId, String jwtToken) {
    log.info("Calling Payment Service (PostgreSQL paymentdb) to get order with id: {}", paymentId);

    String url = this.paymentServiceUrl + "/api/payments/" + paymentId;

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

      ResponseEntity<PaymentDTO> response = restTemplate.exchange(
          url, HttpMethod.GET, entity, PaymentDTO.class);
      log.info("Payment retrieved successfully from payment-service: {}", response.getBody());
      return paymentDTOMapper.toDomain(response.getBody());

    } catch (Exception e) {
      log.error("Error calling Payment Service: {}", e.getMessage());
      throw new RuntimeException("Error calling Payment Service: " + e.getMessage());
    }
  }

  /**
   * Metodo de versión anterior (sin JWT) - mantener para compatibilidad
   * Se puede eliminar una vez que JWT esté completamente implementado
   */
  public Payment getPaymentById(Long paymentId) {
    return getPaymentById(paymentId, null);
  }

  /**
   * Fallback cuando payment-service no está disponible (Sesión 3)
   *
   * Se ejecuta cuando:
   * - El Circuit Breaker está abierto
   * - Se agotaron los reintentos del Retry
   * - payment-service no responde o retorna error
   *
   * @return Order con datos parciales (indica que el servicio no está disponible)
   */
  public Payment getPaymentFallback(Long paymentId, String jwtToken, Throwable throwable) {
    log.warn("FALLBACK: Payment Service no disponible para paymentId: {}. Razón: {}",
        paymentId, throwable.getMessage());

    return Payment.builder()
        .id(paymentId)
        .status("N/A")
        .build();
  }
}
