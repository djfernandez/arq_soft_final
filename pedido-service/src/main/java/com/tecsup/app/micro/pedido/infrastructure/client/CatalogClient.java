package com.tecsup.app.micro.pedido.infrastructure.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecsup.app.micro.pedido.infrastructure.client.dto.CatalogDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogClient {

  private final RestTemplate restTemplate;

  @Value("${catalog.service.url}")
  private String catalogServiceUrl;

  /**
   * Obtiene catálogos por IDs desde catalog-service
   *
   * @param catalogIds IDs de los catálogos a buscar
   * @param jwtToken   Token JWT para autenticación (Sesión 2)
   * @return Lista de catálogos del dominio
   *
   *         Anotaciones Resilience4j (Sesión 3):
   * @CircuitBreaker: Si el 50% de las últimas 10 llamadas fallan,
   *                  abre el circuito por 10 segundos
   * @Retry: Reintenta hasta 3 veces con 1 segundo entre intentos
   */
  @CircuitBreaker(name = "pedidoService")
  @Retry(name = "pedidoService", fallbackMethod = "getCatalogsFallback")
  public List<CatalogDTO> getCatalogsByIds(List<Long> catalogIds, String jwtToken) {
    log.info("Calling Catalog Service to get catalogs with ids: {}", catalogIds);

    String url = this.catalogServiceUrl + "/api/catalogs/ids?ids=" + catalogIds.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));

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

      ResponseEntity<CatalogDTO[]> response = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          CatalogDTO[].class);
      CatalogDTO[] catalogsArray = response.getBody();
      List<CatalogDTO> catalogs = List.of(catalogsArray);
      log.info("Catalogs retrieved successfully: {}", (Object) catalogs);
      return catalogs;
    } catch (Exception e) {
      log.error("Error calling Catalog Service: {}", e.getMessage());
      throw new RuntimeException("Error calling Catalog Service: " + e.getMessage());
    }
  }

  /**
   * Metodo de versión anterior (sin JWT) - mantener para compatibilidad
   * Se puede eliminar una vez que JWT esté completamente implementado
   */
  public List<CatalogDTO> getCatalogsByIds(Long userId) {
    return getCatalogsByIds(List.of(userId), null);
  }

  /**
   * Fallback cuando catalog-service no está disponible (Sesión 3)
   *
   * Se ejecuta cuando:
   * - El Circuit Breaker está abierto
   * - Se agotaron los reintentos del Retry
   * - catalog-service no responde o retorna error
   *
   * Debe devolver el mismo tipo que getCatalogsByIds para que Resilience4j pueda
   * resolver el fallback.
   */
  public List<CatalogDTO> getCatalogsFallback(List<Long> catalogIds, String jwtToken, Throwable throwable) {
    log.warn("FALLBACK: Catalog Service no disponible para catalogIds: {}. Razón: {}",
        catalogIds, throwable.getMessage());

    if (catalogIds == null || catalogIds.isEmpty()) {
      return List.of();
    }

    CatalogDTO fallbackCatalog = new CatalogDTO();
    fallbackCatalog.setId(catalogIds.get(0));
    fallbackCatalog.setName("Catálogo no disponible");
    fallbackCatalog.setDescription("N/A");
    return List.of(fallbackCatalog);
  }
}
