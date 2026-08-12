package com.tecsup.app.micro.pago.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetAllPaymentsUseCase {
  private final PaymentRepository paymentRepository;

  public List<Payment> execute() {
    log.debug("Executing GetAllPaymentsUseCase");
    return paymentRepository.findAll();
  }
}
