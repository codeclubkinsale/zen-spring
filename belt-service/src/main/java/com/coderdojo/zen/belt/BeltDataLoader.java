package com.coderdojo.zen.belt;

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
class BeltDataLoader implements CommandLineRunner {

    /**
     * Javadoc
     */
    private static final Logger log = LoggerFactory.getLogger(BeltDataLoader.class);
    /**
     * Javadoc
     */
    private final ObjectMapper objectMapper;
    /**
     * Javadoc
     */
    private final BeltRepository beltRepository;

    /**
     * Javadoc
     *
     * @param objectMapper Example
     * @param beltRepository Example
     */
    BeltDataLoader(ObjectMapper objectMapper, BeltRepository beltRepository) {
        this.objectMapper = objectMapper;
        this.beltRepository = beltRepository;
    }

    /**
     * Javadoc
     *
     * @param args Example
     */
    @Override
    public void run(String... args) throws Exception {
        if(beltRepository.count() == 0){
            String beltsJSON = "/data/belts.json";
            log.info("Loading belts into database from JSON: {}", beltsJSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(beltsJSON)) {
                Belts response = objectMapper.readValue(inputStream, Belts.class);
                beltRepository.saveAll(response.belts());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
