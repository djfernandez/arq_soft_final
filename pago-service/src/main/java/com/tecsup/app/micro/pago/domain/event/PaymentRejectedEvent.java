package com.tecsup.app.micro.pago.domain.event;

import com.tecsup.app.micro.pago.shared.domain.event.DomainEvent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor(force = true)
public class PaymentRejectedEvent extends DomainEvent {

  private final String paymentId;
  private final String enrollmentId;
  private final String status;

  @Override
  public String getKey() { // SOBREESCRIBIR EL METODO
    return this.paymentId;
  }

}
