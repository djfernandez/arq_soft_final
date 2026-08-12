package com.tecsup.app.micro.pago.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.exception.PaymentNotFoundException;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.model.User;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;
import com.tecsup.app.micro.pago.infrastructure.client.UserClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPaymentByUserIdUseCase {

  private final PaymentRepository paymentRepository;
  private final UserClient userClient;

  public List<Payment> execute(Long id, String jwtToken) {
    log.debug("Executing GetPaymentByIdUseCase for id: {}", id);

    User user = userClient.getUserById(id, jwtToken);
    if (user == null) {
      log.warn("User with id {} not found", id);
      throw new PaymentNotFoundException(id);
    }

    return paymentRepository.findByUserId(id);
  }
}
