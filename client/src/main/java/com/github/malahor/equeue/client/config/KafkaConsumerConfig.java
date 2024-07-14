package com.github.malahor.equeue.client.config;

import java.util.HashMap;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.BooleanDeserializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Bean
  public ConsumerFactory<UUID, Integer> confirmConsumerFactory() {
    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<UUID, Integer> confirmListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<UUID, Integer>();
    factory.setConsumerFactory(confirmConsumerFactory());
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }

  @Bean
  public ConsumerFactory<UUID, Boolean> approveConsumerFactory() {
    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, BooleanDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<UUID, Boolean> approveListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<UUID, Boolean>();
    factory.setConsumerFactory(approveConsumerFactory());
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }
}
