package com.github.malahor.equeue.server.config;

import com.github.malahor.equeue.domain.Customer;
import java.util.HashMap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Bean
  public ConsumerFactory<String, String> registerConsumerFactory() {
    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(props);
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, String> registerListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(registerConsumerFactory());
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Customer> formConsumerFactory() {
    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    return new DefaultKafkaConsumerFactory<>(
        props, new StringDeserializer(), new JsonDeserializer<>(Customer.class));
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<String, Customer> formListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, Customer>();
    factory.setConsumerFactory(formConsumerFactory());
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }
}
