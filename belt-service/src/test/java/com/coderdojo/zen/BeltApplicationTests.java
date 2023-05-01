package com.coderdojo.zen;

import com.coderdojo.zen.belt.dto.BeltRequest;
import com.coderdojo.zen.belt.repositories.BeltRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class BeltApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BeltRepository beltRepository;

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        BeltRequest beltRequest = getBeltRequest();
        String beltRequestString = objectMapper.writeValueAsString(beltRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/belts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beltRequestString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, beltRepository.findAll().size());
    }

    private BeltRequest getBeltRequest() {
        return BeltRequest.builder()
                .name("White Belt")
                .description("White Belt description")
                .imageURL("some url")
                .build();
    }

}
