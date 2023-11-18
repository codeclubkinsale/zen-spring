package com.coderdojo.zen.belt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
class BeltDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(BeltDataLoader.class);
    private final ObjectMapper objectMapper;
    private final BeltRepository beltRepository;

    public BeltDataLoader(ObjectMapper objectMapper, BeltRepository beltRepository) {
        this.objectMapper = objectMapper;
        this.beltRepository = beltRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(beltRepository.count() == 0){
            String BELTS_JSON = "/data/belts.json";
            log.info("Loading belts into database from JSON: {}", BELTS_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(BELTS_JSON)) {
                Belts response = objectMapper.readValue(inputStream, Belts.class);
                beltRepository.saveAll(response.posts());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }
        }
    }

}
