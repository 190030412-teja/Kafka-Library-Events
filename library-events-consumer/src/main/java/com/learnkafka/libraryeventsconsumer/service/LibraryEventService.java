package com.learnkafka.libraryeventsconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.libraryeventsconsumer.Entity.LibraryEvent;
import com.learnkafka.libraryeventsconsumer.jpa.LibraryEventsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class LibraryEventService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private LibraryEventsRepository libraryEventsRepository;

    public void processLibraryEvent(String data) throws JsonProcessingException {
        LibraryEvent libraryEvent=objectMapper.readValue(data, LibraryEvent.class);
        log.info("LibrabryEvent : {}", libraryEvent);
        switch (libraryEvent.getLibraryEventType()){
            case NEW :
                //save
                save(libraryEvent);
                break;
            case UPDATE:
                //validate
                validate(libraryEvent);
                //update
                save(libraryEvent);
                break;
            default:
                log.info("Invalid Library Event Type");
        }

    }

    private void validate(LibraryEvent libraryEvent) {
        if(libraryEvent.getLibraryEventId()==null){
            throw new IllegalArgumentException("LibraryEvent Id is missing");
        }
        Optional<LibraryEvent> libraryEvent1=libraryEventsRepository.findById(libraryEvent.getLibraryEventId());
        if(!libraryEvent1.isPresent()){
            throw new IllegalArgumentException("Not a Valid Library Event");
        }
        log.info("Validation is Successful for Library event: {}",libraryEvent);
    }

    private void save(LibraryEvent libraryEvent) {
        libraryEvent.getBook().setLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        log.info("Successfully saved LibraryEvent {}",libraryEvent);
    }

}
