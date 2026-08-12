package com.tecsup.app.micro.pago.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPaymentByIdUseCase {

  private final PaymentRepository paymentRepository;

  public Payment execute(Long id, String jwtToken) {
    log.debug("Executing GetPaymentByIdUseCase for id: {}", id);

    return paymentRepository.findById(id).orElse(null);
  }
}
