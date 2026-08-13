package com.tecsup.app.micro.pago.application.usecase;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.pago.domain.event.PaymentApprovedEvent;
import com.tecsup.app.micro.pago.domain.event.PaymentRejectedEvent;
import com.tecsup.app.micro.pago.domain.exception.InvalidPaymentDataException;
import com.tecsup.app.micro.pago.domain.model.Order;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.model.User;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;
import com.tecsup.app.micro.pago.infrastructure.client.OrderClient;
import com.tecsup.app.micro.pago.infrastructure.client.UserClient;
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
  private final UserClient userClient;

  public Payment execute(Payment payment, String jwtToken) {
    log.debug("Executing CreatePaymentUseCase for orderId: {}", payment.getOrderId());
    if (!payment.isValid()) {
      throw new InvalidPaymentDataException("Invalid payment data. OrderId and positive amount are required.");
    }

    User user = userClient.getUserById(payment.getUserId(), jwtToken);
    if (user == null || user.getId() == null) {
      throw new InvalidPaymentDataException("User with ID " + payment.getUserId() + " does not exist.");
    }

    Order order = orderClient.getOrderById(payment.getOrderId(), jwtToken);
    if (order == null || order.getId() == null) {
      throw new InvalidPaymentDataException("Order with ID " + payment.getOrderId() + " does not exist.");
    }

    if (this.random.nextInt(10) < 2) { // Simulate a 20% chance of failure
      payment.setStatus("CANCELLED");
      Payment savedPayment = paymentRepository.save(payment);
      if (savedPayment == null || savedPayment.getId() == null) {
        throw new InvalidPaymentDataException("Payment could not be saved before publishing rejection event.");
      }

      log.warn("Payment failed for orderId: {}", payment.getOrderId());
      PaymentRejectedEvent event = new PaymentRejectedEvent(
          savedPayment.getId().toString(),
          savedPayment.getOrderId().toString(),
          savedPayment.getStatus());
      log.info("Publishing paymentRejectedEvent for paymentId: {}", savedPayment.getId());
      eventPublisher.publish(event);
      return savedPayment;
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

    return savedPayment;
  }
}
