package com.tecsup.app.micro.pago.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.exception.PaymentNotFoundException;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatePaymentUseCase {
  private final PaymentRepository paymentRepository;

  public Payment execute(Long id, Payment payment, String jwtToken) {
    log.debug("Executing UpdatePaymentUseCase for id: {}", id);
    Payment existing = paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    if (payment.getStatus() != null)
      existing.setStatus(payment.getStatus());
    return paymentRepository.save(existing);
  }
}
