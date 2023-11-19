package com.coderdojo.zen.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Javadoc
 */
@Component
class EventDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(EventDataLoader.class);
    private final ObjectMapper objectMapper;
    private final EventRepository eventRepository;

    /**
     * Javadoc
     */
    public EventDataLoader(ObjectMapper objectMapper, EventRepository eventRepository) {
        this.objectMapper = objectMapper;
        this.eventRepository = eventRepository;
    }

    /**
     * Javadoc
     */
    @Override
    public void run(String... args) throws Exception {
        if(eventRepository.count() == 0){
            String DOJOS_JSON = "/data/events.json";
            log.info("Loading events into database from JSON: {}", DOJOS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(DOJOS_JSON)) {
                Events response = objectMapper.readValue(inputStream, Events.class);
                eventRepository.saveAll(response.events());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
