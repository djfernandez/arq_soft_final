package com.tecsup.app.micro.pago.application.usecase;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.event.PaymentApprovedEvent;
import com.tecsup.app.micro.pago.domain.event.PaymentRejectedEvent;
import com.tecsup.app.micro.pago.domain.exception.InvalidPaymentDataException;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;
import com.tecsup.app.micro.pago.infrastructure.client.OrderClient;
import com.tecsup.app.micro.pago.shared.infrastructure.event.KafkaEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentUseCase {
  private final PaymentRepository paymentRepository;
  private final KafkaEventPublisher eventPublisher;
  private final Random random = new Random();
  private final OrderClient orderClient;

  public Payment execute(Payment payment, String jwtToken) {
    log.debug("Executing CreatePaymentUseCase for orderId: {}", payment.getOrderId());
    if (!payment.isValid()) {
      throw new InvalidPaymentDataException("Invalid payment data. OrderId and positive amount are required.");
    }

    if (this.random.nextInt(10) < 2) { // Simulate a 20% chance of failure
      payment.setStatus("CANCELLED");
      log.warn("Payment failed for orderId: {}", payment.getOrderId());
      PaymentRejectedEvent event = new PaymentRejectedEvent(
          payment.getId().toString(),
          payment.getOrderId().toString(),
          payment.getStatus());
      log.info("Publishing paymentRejectedEvent for paymentId: {}", payment.getId());
      eventPublisher.publish(event);
      return payment;
    }
    payment.setStatus("APPROVED");
    Payment savedPayment = paymentRepository.save(payment);
    log.info("Payment created with id: {}", savedPayment.getId());

    PaymentApprovedEvent event = new PaymentApprovedEvent(
        savedPayment.getId().toString(),
        savedPayment.getOrderId().toString(),
        savedPayment.getStatus());
    log.info("Publishing paymentApprovedEvent for paymentId: {}", savedPayment.getId());
    eventPublisher.publish(event);

    orderClient.getOrderById(savedPayment.getOrderId(), jwtToken);

    return savedPayment;
  }
}
