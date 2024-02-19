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

    /**
     * Javadoc
     */
    private static final Logger log = LoggerFactory.getLogger(BadgeDataLoader.class);

    /**
     * Javadoc
     */
    private final ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    private final BadgeRepository badgeRepository;

    /**
     * Javadoc
     *
     * @param objectMapper Example
     * @param badgeRepository Example
     */
    BadgeDataLoader(ObjectMapper objectMapper, BadgeRepository badgeRepository) {
        this.objectMapper = objectMapper;
        this.badgeRepository = badgeRepository;
    }

    /**
     * Javadoc
     *
     * @param args Example
     * @throws Exception Example
     */
    @Override
    public void run(String... args) throws Exception {
        if(badgeRepository.count() == 0){
            String badgesJSON = "/data/badges.json";
            log.info("Loading badges into database from JSON: {}", badgesJSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(badgesJSON)) {
                Badges response = objectMapper.readValue(inputStream, Badges.class);
                badgeRepository.saveAll(response.badges());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
