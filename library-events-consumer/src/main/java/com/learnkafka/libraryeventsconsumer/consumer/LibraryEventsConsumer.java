package com.learnkafka.libraryeventsconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.libraryeventsconsumer.service.LibraryEventService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryEventsConsumer {

    @Autowired
    private LibraryEventService libraryEventService;

    @KafkaListener(topics = {"${spring.kafka.topic}"})
    public void onMessage(String data) throws JsonProcessingException {

        log.info("Consumer Record :{}",data);
        libraryEventService.processLibraryEvent(data);
        //ConsumerRecord<Integer, String> consumerRecord
    }



}
