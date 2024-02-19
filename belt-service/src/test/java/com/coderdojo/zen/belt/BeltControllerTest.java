package com.coderdojo.zen.belt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Javadoc
 */
@WebMvcTest(BeltController.class)
@AutoConfigureMockMvc
class BeltControllerTest {

    /**
     * Javadoc
     */
    @Autowired
    MockMvc mockMvc;

    /**
     * Javadoc
     */
    @MockBean
    BeltRepository repository;

    /**
     * Javadoc
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    List<Belt> belts = new ArrayList<>();

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BeltControllerTest() { /* Default Constructor */ }

    /**
     * Javadoc
     */
    @BeforeEach
    void setUp() {
        belts = List.of(
                new Belt(1,"Test Name One", "Test Description One","Test Image One",null),
                new Belt(2,"Test Name Two", "Test Description Two","Test Image Two",null)
        );
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldFindAllBelts() throws Exception {
        String jsonResponse = """
                [
                    {
                        "id":1,
                        "name":"Test Name One",
                        "description":"Test Description One",
                        "image":"Test Image One",
                        "version": null
                    },
                    {
                        "id":2,
                        "name":"Test Name Two",
                        "description":"Test Description Two",
                        "image":"Test Image Two",
                        "version": null
                    }
                ]
                """;

        when(repository.findAll()).thenReturn(belts);

        ResultActions resultActions = mockMvc.perform(get("/api/belts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);

    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldFindBeltWhenGivenValidId() throws Exception {
        Belt belt = new Belt(1,"Test Title", "Test Body","Test Body",null);
        when(repository.findById(1)).thenReturn(Optional.of(belt));


        mockMvc.perform(get("/api/belts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(belt.name())))
                .andExpect(jsonPath("$.description",
                        is(belt.description())))
                .andExpect(jsonPath("$.image",
                        is(belt.image())));
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldCreateNewBeltWhenGivenValidID() throws Exception {
        Belt belt = new Belt(1,"Test Title", "Test Body","Test Body",null);
        when(repository.save(belt)).thenReturn(belt);

        mockMvc.perform(post("/api/belts")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(belt)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                is(belt.name())))
                .andExpect(jsonPath("$.description",
                        is(belt.description())))
                .andExpect(jsonPath("$.image",
                        is(belt.image())));
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldUpdateBeltWhenGivenValidBelt() throws Exception {
        Belt updated = new Belt(1,"Test Title", "Test Body","Test Body",null);
        when(repository.findById(1)).thenReturn(Optional.of(belts.get(0)));
        when(repository.save(updated)).thenReturn(updated);


        mockMvc.perform(put("/api/belts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidBeltID() throws Exception {
        Belt updated = new Belt(1,"Test Title", "Test Body","Test Body",null);
        when(repository.save(updated)).thenReturn(updated);


        mockMvc.perform(put("/api/belts/999")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeleteBeltWhenGivenValidID() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete("/api/belts/1"))
                .andExpect(status().isNoContent());

        verify(repository, times(1)).deleteById(1);
    }

}
