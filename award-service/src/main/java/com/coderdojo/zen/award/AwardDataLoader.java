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

    private static final Logger log = LoggerFactory.getLogger(AwardDataLoader.class);
    private final ObjectMapper objectMapper;
    private final AwardRepository awardRepository;

    /**
     * Javadoc
     */
    public AwardDataLoader(ObjectMapper objectMapper, AwardRepository awardRepository) {
        this.objectMapper = objectMapper;
        this.awardRepository = awardRepository;
    }

    /**
     * Javadoc
     */
    @Override
    public void run(String... args) throws Exception {
        if(awardRepository.count() == 0){
            String DOJOS_JSON = "/data/awards.json";
            log.info("Loading awards into database from JSON: {}", DOJOS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(DOJOS_JSON)) {
                Awards response = objectMapper.readValue(inputStream, Awards.class);
                awardRepository.saveAll(response.awards());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
