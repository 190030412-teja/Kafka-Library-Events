package com.example.libraryeventsproducer.producer;

import com.example.libraryeventsproducer.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class LibraryEventsProducer {

    @Value("${spring.kafka.topic}")
    private String topic;
    private final KafkaTemplate<Integer, String> kafkaTemplate;

    private final ObjectMapper objectMapper;


    public LibraryEventsProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<SendResult<Integer, String>> sendLibraryEvent(LibraryEvent libraryEvent) throws JsonProcessingException {

        var key= libraryEvent.libraryEventId();
        var value=objectMapper.writeValueAsString(libraryEvent);

        var completableFuture=kafkaTemplate.send(topic,key,value);

        return completableFuture.whenComplete((SendResult, throwable) -> {
           if(throwable!=null) {
                handleFailure(key, value, throwable);
           }else{
                handleSuccess(key, value, SendResult);
           }
        });

    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
        log.info("Send Message Successfully for the key : {}, value : {}, partion :{}", key, value , sendResult.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, String value, Throwable throwable) {
        log.error("Error Sending the Message exprection occured {}", throwable.getMessage(), throwable);
    }



}
