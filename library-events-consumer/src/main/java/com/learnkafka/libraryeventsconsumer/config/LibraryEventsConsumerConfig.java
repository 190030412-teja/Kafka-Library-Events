package com.learnkafka.libraryeventsconsumer.config;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
@EnableKafka
public class LibraryEventsConsumerConfig {
    
    // @Bean
    // @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    // ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
    //         ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
    //         ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
    //     ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    //     // factory.setConcurrency(3);
    //     // factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    //     return factory;
    // }

}
