package com.example.libraryeventsproducer.controller;

import com.example.libraryeventsproducer.domain.LibraryEvent;
import com.example.libraryeventsproducer.domain.LibraryEventType;
import com.example.libraryeventsproducer.producer.LibraryEventsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    private final LibraryEventsProducer libraryEventsProducer;

    public LibraryEventsController(LibraryEventsProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<?> postLibraryEvent(@RequestBody  LibraryEvent libraryEvent) throws JsonProcessingException {

        log.info("library Event: {}",libraryEvent);
        //invoke kafka producer
        libraryEventsProducer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent")
    public ResponseEntity<?> updateLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException {
        log.info("library Event: {}",libraryEvent);
        //validate

        ResponseEntity<String> BAD_REQUEST = validateLibraryEvent(libraryEvent);

        if(BAD_REQUEST!=null){
            return BAD_REQUEST;
        }

        //invoke kafka producer
        libraryEventsProducer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }

    public ResponseEntity<String> validateLibraryEvent(LibraryEvent libraryEvent){
        if(libraryEvent.libraryEventId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please add the LibraryEventId");
        }
        if(!(libraryEvent.libraryEventType().equals(LibraryEventType.UPDATE))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("only UPDATE Event is accepted");
        }
        return null;
    }

}
