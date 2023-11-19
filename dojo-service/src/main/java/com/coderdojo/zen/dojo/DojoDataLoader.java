package com.coderdojo.zen.dojo;

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
class DojoDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DojoDataLoader.class);
    private final ObjectMapper objectMapper;
    private final DojoRepository dojoRepository;

    /**
     * Javadoc
     */
    public DojoDataLoader(ObjectMapper objectMapper, DojoRepository dojoRepository) {
        this.objectMapper = objectMapper;
        this.dojoRepository = dojoRepository;
    }

    /**
     * Javadoc
     */
    @Override
    public void run(String... args) throws Exception {
        if(dojoRepository.count() == 0){
            String DOJOS_JSON = "/data/dojos.json";
            log.info("Loading dojos into database from JSON: {}", DOJOS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(DOJOS_JSON)) {
                Dojos response = objectMapper.readValue(inputStream, Dojos.class);
                dojoRepository.saveAll(response.dojos());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
