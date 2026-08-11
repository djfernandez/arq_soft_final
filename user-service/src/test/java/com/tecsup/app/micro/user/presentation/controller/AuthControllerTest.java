package com.tecsup.app.micro.user.presentation.controller;

import com.tecsup.app.micro.user.infrastructure.config.JwtTokenProvider;
import com.tecsup.app.micro.user.infrastructure.security.CustomUserDetailsService;
import com.tecsup.app.micro.user.presentation.dto.LoginRequest;
import com.tecsup.app.micro.user.presentation.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthControllerTest {

  private final AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
  private final JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);
  private final CustomUserDetailsService customUserDetailsService = mock(CustomUserDetailsService.class);
  private final AuthController authController = new AuthController(
      authenticationManager,
      jwtTokenProvider,
      customUserDetailsService);

  @Test
  void loginShouldReturnTokenWhenCredentialsAreValid() {
    LoginRequest request = LoginRequest.builder()
        .email("juan@example.com")
        .password("secret123")
        .build();

    UserDetails userDetails = User.withUsername("juan@example.com")
        .password("encoded")
        .authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))
        .build();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    when(customUserDetailsService.loadUserByUsername("juan@example.com")).thenReturn(userDetails);
    when(jwtTokenProvider.generateToken(userDetails)).thenReturn("jwt-token");

    ResponseEntity<?> responseEntity = authController.login(request);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isInstanceOf(LoginResponse.class);

    LoginResponse response = (LoginResponse) responseEntity.getBody();
    assertThat(response.getToken()).isEqualTo("jwt-token");
    assertThat(response.getEmail()).isEqualTo("juan@example.com");
    assertThat(response.getRoles()).containsExactly("ROLE_ADMIN");

    verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(customUserDetailsService).loadUserByUsername("juan@example.com");
    verify(jwtTokenProvider).generateToken(userDetails);
  }

  @Test
  void loginShouldReturnUnauthorizedWhenCredentialsAreInvalid() {
    LoginRequest request = LoginRequest.builder()
        .email("juan@example.com")
        .password("wrong")
        .build();

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    ResponseEntity<?> responseEntity = authController.login(request);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(responseEntity.getBody()).isInstanceOf(Map.class);

    Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
    assertThat(body.get("error")).isEqualTo("Credenciales inválidas");
    assertThat(body.get("status")).isEqualTo(401);
  }
}
