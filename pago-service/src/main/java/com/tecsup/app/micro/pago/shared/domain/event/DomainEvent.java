package com.tecsup.app.micro.pago.shared.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class DomainEvent {
  private final String eventId;

  private final String eventType;

  private final LocalDateTime occurredOn;

  public DomainEvent() {
    this.eventId = UUID.randomUUID().toString();
    this.eventType = this.getClass().getSimpleName();
    this.occurredOn = LocalDateTime.now();
  }

  /**
   * Obtener la clave del evento para particionamiento en Kafka
   *
   * @return
   */
  public String getKey() {
    throw new RuntimeException("Method getKey() not implemented");
  }
}
