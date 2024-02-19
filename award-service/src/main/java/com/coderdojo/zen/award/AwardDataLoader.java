package com.coderdojo.zen.award;

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
class AwardDataLoader implements CommandLineRunner {

    /**
     * Javadoc
     */
    private static final Logger log = LoggerFactory.getLogger(AwardDataLoader.class);

    /**
     * Javadoc
     */
    private final ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    private final AwardRepository awardRepository;

    /**
     * Javadoc
     *
     * @param objectMapper Example
     * @param awardRepository Example
     */
    AwardDataLoader(ObjectMapper objectMapper, AwardRepository awardRepository) {
        this.objectMapper = objectMapper;
        this.awardRepository = awardRepository;
    }

    /**
     * Javadoc
     *
     * @param args Example
     */
    @Override
    public void run(String... args) throws Exception {
        if(awardRepository.count() == 0){
            String awardsJSON = "/data/awards.json";
            log.info("Loading awards into database from JSON: {}", awardsJSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(awardsJSON)) {
                Awards response = objectMapper.readValue(inputStream, Awards.class);
                awardRepository.saveAll(response.awards());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
