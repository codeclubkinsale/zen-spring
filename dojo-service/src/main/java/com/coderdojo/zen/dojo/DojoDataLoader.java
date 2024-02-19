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

    /**
     * Javadoc
     */
    private static final Logger log = LoggerFactory.getLogger(DojoDataLoader.class);

    /**
     * Javadoc
     */
    private final ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    private final DojoRepository dojoRepository;

    /**
     * Javadoc
     *
     * @param objectMapper Example
     * @param dojoRepository Example
     */
    DojoDataLoader(ObjectMapper objectMapper, DojoRepository dojoRepository) {
        this.objectMapper = objectMapper;
        this.dojoRepository = dojoRepository;
    }

    /**
     * Javadoc
     *
     * @param args Example
     */
    @Override
    public void run(String... args) throws Exception {
        if(dojoRepository.count() == 0){
            String dojosJSON = "/data/dojos.json";
            log.info("Loading dojos into database from JSON: {}", dojosJSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(dojosJSON)) {
                Dojos response = objectMapper.readValue(inputStream, Dojos.class);
                dojoRepository.saveAll(response.dojos());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
