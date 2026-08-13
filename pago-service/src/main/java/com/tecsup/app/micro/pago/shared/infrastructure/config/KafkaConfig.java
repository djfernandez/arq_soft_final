package com.tecsup.app.micro.pago.shared.infrastructure.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tecsup.app.micro.pago.shared.domain.event.DomainEvent;

@EnableKafka
@Configuration
public class KafkaConfig {
  // Set TOPICS
  public static final String PAYMENT_EVENT_TOPIC = "payment.events";

  // Set QUEUES/PARTITIONS

  /**
   * Topic de eventos de pago
   *
   * @return
   */
  @Bean
  public NewTopic paymentEventTopic() {

    return new NewTopic(PAYMENT_EVENT_TOPIC, // topic
        3, // Nro. particiones
        (short) 1 // Nro. de replicas
    );
  }

  @Bean
  public ProducerFactory<String, DomainEvent> producerFactory(
      @Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrapServers) {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public KafkaTemplate<String, DomainEvent> kafkaTemplate(
      ProducerFactory<String, DomainEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public DefaultKafkaConsumerFactory<String, DomainEvent> consumerFactory(
      @Value("${spring.kafka.bootstrap-servers:localhost:9092}") String bootstrapServers) {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-notifications-group");
    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "com.tecsup.app.micro.pago.*");
    configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
    configProps.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS, false);
    return new DefaultKafkaConsumerFactory<>(
        configProps,
        new StringDeserializer(),
        new JsonDeserializer<>(DomainEvent.class));
  }

  @Bean(name = "kafkaListenerContainerFactory")
  public ConcurrentKafkaListenerContainerFactory<String, DomainEvent> kafkaListenerContainerFactory(
      DefaultKafkaConsumerFactory<String, DomainEvent> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, DomainEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    return factory;
  }
}
