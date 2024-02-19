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

    /**
     * Javadoc
     */
    private static final Logger log = LoggerFactory.getLogger(EventDataLoader.class);

    /**
     * Javadoc
     */
    private final ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    private final EventRepository eventRepository;

    /**
     * Javadoc
     *
     * @param objectMapper Example
     * @param eventRepository Example
     */
    EventDataLoader(ObjectMapper objectMapper, EventRepository eventRepository) {
        this.objectMapper = objectMapper;
        this.eventRepository = eventRepository;
    }

    /**
     * Javadoc
     *
     * @param args Example
     */
    @Override
    public void run(String... args) throws Exception {
        if(eventRepository.count() == 0){
            String eventsJSON = "/data/events.json";
            log.info("Loading events into database from JSON: {}", eventsJSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(eventsJSON)) {
                Events response = objectMapper.readValue(inputStream, Events.class);
                eventRepository.saveAll(response.events());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
