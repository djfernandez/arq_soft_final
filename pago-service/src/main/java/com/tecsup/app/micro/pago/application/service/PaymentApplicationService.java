package com.tecsup.app.micro.pago.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsup.app.micro.pago.application.usecase.CreatePaymentUseCase;
import com.tecsup.app.micro.pago.application.usecase.DeletePaymentUseCase;
import com.tecsup.app.micro.pago.application.usecase.GetAllPaymentsUseCase;
import com.tecsup.app.micro.pago.application.usecase.GetPaymentByIdUseCase;
import com.tecsup.app.micro.pago.application.usecase.GetPaymentByUserIdUseCase;
import com.tecsup.app.micro.pago.application.usecase.UpdatePaymentUseCase;
import com.tecsup.app.micro.pago.domain.model.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentApplicationService {

  private final GetAllPaymentsUseCase getAllPaymentsUseCase;
  private final GetPaymentByIdUseCase getPaymentByIdUseCase;
  private final GetPaymentByUserIdUseCase getPaymentByUserIdUseCase;
  private final CreatePaymentUseCase createPaymentUseCase;
  private final UpdatePaymentUseCase updatePaymentUseCase;
  private final DeletePaymentUseCase deletePaymentUseCase;

  @Transactional(readOnly = true)
  public List<Payment> getAllPayments() {
    return getAllPaymentsUseCase.execute();
  }

  @Transactional(readOnly = true)
  public Payment getPaymentById(Long id, String jwtToken) {
    return getPaymentByIdUseCase.execute(id, jwtToken);
  }

  @Transactional(readOnly = true)
  public List<Payment> getPaymentsByUserId(Long userId, String jwtToken) {
    return getPaymentByUserIdUseCase.execute(userId, jwtToken);
  }

  @Transactional
  public Payment createPayment(Payment payment, String jwtToken) {
    return createPaymentUseCase.execute(payment, jwtToken);
  }

  @Transactional
  public Payment updatePayment(Long id, Payment payment, String jwtToken) {
    return updatePaymentUseCase.execute(id, payment, jwtToken);
  }

  @Transactional
  public void deletePayment(Long id) {
    deletePaymentUseCase.execute(id);
  }
}
