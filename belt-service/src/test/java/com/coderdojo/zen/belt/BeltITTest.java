package com.coderdojo.zen.belt;

import com.coderdojo.zen.belt.model.Belt;
import com.coderdojo.zen.belt.repositories.BeltRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BeltITTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeltRepository beltRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        beltRepository.deleteAll();
    }

    @Test
    @Order(1)
    void givenBeltObject_whenCreateBelt_thenReturnSavedBelt() throws Exception{

        // given - precondition or setup
        Belt belt = new Belt(
                null,
                "Scratch 2",
                "This is a Scratch belt.",
                "image"
        );

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/belts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(belt)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(belt.getName())))
                .andExpect(jsonPath("$.description",
                        is(belt.getDescription())))
                .andExpect(jsonPath("$.imageURL",
                        is(belt.getImageURL())));

    }

    // JUnit test for Get All belts REST API
    @Test
    @Order(2)
    void givenListOfBelts_whenGetAllBelts_thenReturnBeltsList() throws Exception{
        // given - precondition or setup
        List<Belt> listOfBelts = new ArrayList<>();
        listOfBelts.add(new Belt(1L,"MacBook Pro", "", ""));
        listOfBelts.add(new Belt(2L,"iPhone", "", ""));
        beltRepository.saveAll(listOfBelts);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/belts"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfBelts.size())));

    }

    @Test
    @Order(3)
    void givenListOfBelts_whenGetBeltsByCategory_thenReturnBeltsForCategory() throws Exception{
        // given - precondition or setup
        List<Belt> listOfBelts = new ArrayList<>();
        listOfBelts.add(new Belt(null,"One", "one", ""));
        listOfBelts.add(new Belt(null,"Two", "two", ""));
        beltRepository.saveAll(listOfBelts);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/belts")
                .param("category", "soft_skills"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfBelts.size())));

    }

    @Test
    @Order(4)
    void givenListOfBelts_whenGetBeltsByInvalidCategory_thenReturnAllBelts() throws Exception{
        // given - precondition or setup
        List<Belt> listOfBelts = new ArrayList<>();
        listOfBelts.add(new Belt(null,"One", "one", ""));
        listOfBelts.add(new Belt(null,"Two", "two", ""));
        beltRepository.saveAll(listOfBelts);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/belts")
                .param("category", "fake"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfBelts.size())));

    }

    // positive scenario - valid belt id
    // JUnit test for GET belt by id REST API
    @Test
    @Order(5)
    void givenBeltId_whenGetBeltById_thenReturnBeltObject() throws Exception{
        // given - precondition or setup
        Belt belt = beltRepository.save(
                new Belt(null,"JavaScript", "This is a JavaScript belt", "")
        );
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/belts/{id}", belt.getId()));
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.description",
                        is(belt.getDescription())))
                .andExpect(jsonPath("$.imageURL",
                        is(belt.getImageURL())));

    }

    // negative scenario - valid belt id
    // JUnit test for GET belt by id REST API
    @Test
    @Order(6)
    void givenInvalidBeltId_whenGetBeltById_thenReturnEmpty() throws Exception{

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/belts/{id}", 11L));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // JUnit test for update belt REST API - positive scenario
    @Test
    @Order(7)
    void givenUpdatedBelt_whenUpdateBelt_thenReturnUpdateBeltObject() throws Exception{
        // given - precondition or setup
        Belt belt = beltRepository.save(
                new Belt(null,
                        "Python",
                        "This is a Javascript belt",
                        ""
                )
        );
        Belt updatedBelt = new Belt(belt.getId(),"Python", "This is a Python belt", "");
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/belts/{id}", belt.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Python\",\"description\":\"This is a Python belt\",\"category\":\"PROGRAMMING\"}"));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.description",
                        is(updatedBelt.getDescription())))
                .andExpect(jsonPath("$.imageURL",
                        is(updatedBelt.getImageURL())));
    }

    // JUnit test for update belt REST API - negative scenario
    @Test
    @Order(8)
    void givenUpdatedBelt_whenUpdateBelt_thenReturn404() throws Exception{
        // given - precondition or setup

        Belt belt = new Belt(
                15L,
                "Swift",
                "This is a swift belt",
                ""
        );

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/belts/{id}", belt.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(belt)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for delete belt REST API
    @Test
    @Order(9)
    void givenBeltId_whenDeleteBelt_thenReturn200() throws Exception{
        // given - precondition or setup
        Belt belt = beltRepository.save(
                new Belt(
                        null,
                        "Python",
                        "This is a Javascript belt",
                        ""
                )
        );

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/belts/{id}", belt.getId()));

        // then - verify the output
        response.andExpect(status().isNoContent())
                .andDo(print());
    }
}
