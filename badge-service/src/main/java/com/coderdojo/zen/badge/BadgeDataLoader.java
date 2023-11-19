package com.coderdojo.zen.badge;

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
class BadgeDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BadgeDataLoader.class);
    private final ObjectMapper objectMapper;
    private final BadgeRepository badgeRepository;

    /**
     * Javadoc
     */
    public BadgeDataLoader(ObjectMapper objectMapper, BadgeRepository badgeRepository) {
        this.objectMapper = objectMapper;
        this.badgeRepository = badgeRepository;
    }

    /**
     * Javadoc
     */
    @Override
    public void run(String... args) throws Exception {
        if(badgeRepository.count() == 0){
            String DOJOS_JSON = "/data/badges.json";
            log.info("Loading badges into database from JSON: {}", DOJOS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(DOJOS_JSON)) {
                Badges response = objectMapper.readValue(inputStream, Badges.class);
                badgeRepository.saveAll(response.badges());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
