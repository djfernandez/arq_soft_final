package com.tecsup.app.micro.pedido.infrastructure.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecsup.app.micro.pedido.infrastructure.client.dto.CatalogDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogClient {

  private final RestTemplate restTemplate;

  @Value("${catalog.service.url}")
  private String catalogServiceUrl;

  public List<CatalogDTO> getProductById(List<Long> productId, String token) {
    log.info("Calling Catalog Service to get product with id: {}", productId);

    String url = this.catalogServiceUrl + "/api/catalogs/ids?ids=" + productId.stream()
        .map(String::valueOf)
        .collect(Collectors.joining(","));

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", token);
      HttpEntity<String> entity = new HttpEntity<>(headers);

      CatalogDTO[] productsArray = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          CatalogDTO[].class).getBody();
      List<CatalogDTO> products = List.of(productsArray);
      log.info("Products retrieved successfully: {}", (Object) products);
      return products;
    } catch (Exception e) {
      log.error("Error calling Product Service: {}", e.getMessage());
      throw new RuntimeException("Error calling Product Service: " + e.getMessage());
    }
  }
}
