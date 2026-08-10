package com.tecsup.app.micro.pedido.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tecsup.app.micro.pedido.infrastructure.client.dto.UserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

  private final RestTemplate restTemplate;

  @Value("${user.service.url}")
  private String userServiceUrl;

  public UserDTO getUserById(Long userId, String token) {
    log.info("Calling User Service (PostgreSQL userdb) to get user with id: {}", userId);

    String url = this.userServiceUrl + "/api/users/" + userId;

    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", token);
      HttpEntity<String> entity = new HttpEntity<>(headers);

      UserDTO user = restTemplate.exchange(
          url,
          HttpMethod.GET,
          entity,
          UserDTO.class).getBody();
      log.info("User retrieved successfully from userdb: {}", user);
      return user;
    } catch (HttpClientErrorException.NotFound e) {
      log.warn("User not found in User Service. userId={}", userId);
      throw e;
    } catch (Exception e) {
      log.error("Error calling User Service: {}", e.getMessage());
      throw new RuntimeException("Error calling User Service: " + e.getMessage());
    }
  }
}