package com.learnkafka.libraryeventsconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsConsumerManualOffset implements AcknowledgingMessageListener<Integer,String> {

    // @KafkaListener(topics = "${spring.kafka.topic}")
    // public void onMessage(ConsumerRecord<Integer,String> consumerRecord){
    //     log.info("ConsumerRecord : {} ", consumerRecord);
    // }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}")
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord, Acknowledgment acknowledgment) {
        log.info("ConsumerRecord : {} ", consumerRecord);
        acknowledgment.acknowledge();
    }

}
