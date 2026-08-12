package com.tecsup.app.micro.pago.application.usecase;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.exception.PaymentNotFoundException;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeletePaymentUseCase {
  private final PaymentRepository paymentRepository;

  public void execute(Long id) {
    log.debug("Executing DeletePaymentUseCase for id: {}", id);
    if (!paymentRepository.findById(id).isPresent())
      throw new PaymentNotFoundException(id);
    paymentRepository.deleteById(id);
  }
}
