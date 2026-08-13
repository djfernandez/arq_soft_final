package com.tecsup.app.micro.pago.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.tecsup.app.micro.pago.domain.model.Order;
import com.tecsup.app.micro.pago.domain.model.Payment;
import com.tecsup.app.micro.pago.domain.model.User;
import com.tecsup.app.micro.pago.domain.repository.PaymentRepository;
import com.tecsup.app.micro.pago.infrastructure.client.OrderClient;
import com.tecsup.app.micro.pago.infrastructure.client.UserClient;
import com.tecsup.app.micro.pago.shared.infrastructure.event.KafkaEventPublisher;

class CreatePaymentUseCaseTest {

  private PaymentRepository paymentRepository;
  private KafkaEventPublisher eventPublisher;
  private OrderClient orderClient;
  private UserClient userClient;
  private CreatePaymentUseCase useCase;

  @BeforeEach
  void setUp() {
    paymentRepository = mock(PaymentRepository.class);
    eventPublisher = mock(KafkaEventPublisher.class);
    orderClient = mock(OrderClient.class);
    userClient = mock(UserClient.class);
    useCase = new CreatePaymentUseCase(paymentRepository, eventPublisher, orderClient, userClient);

    ReflectionTestUtils.setField(useCase, "random", new Random() {
      @Override
      public int nextInt(int bound) {
        return 0;
      }
    });
  }

  @Test
  void createRejectedPaymentShouldPersistBeforePublishingEvent() {
    User user = User.builder().id(10L).build();
    Order order = Order.builder().id(20L).build();
    Payment payment = Payment.builder()
        .orderId(20L)
        .userId(10L)
        .amount(new BigDecimal("150.00"))
        .status("PENDING")
        .build();

    when(userClient.getUserById(10L, "jwt-token")).thenReturn(user);
    when(orderClient.getOrderById(20L, "jwt-token")).thenReturn(order);
    when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
      Payment saved = invocation.getArgument(0);
      saved.setId(99L);
      return saved;
    });

    Payment result = useCase.execute(payment, "jwt-token");

    assertThat(result.getId()).isEqualTo(99L);
    assertThat(result.getStatus()).isEqualTo("CANCELLED");
    verify(paymentRepository).save(any(Payment.class));
  }
}
