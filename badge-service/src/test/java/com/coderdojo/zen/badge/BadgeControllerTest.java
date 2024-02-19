package com.coderdojo.zen.badge;

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
@WebMvcTest(BadgeController.class)
@AutoConfigureMockMvc
class BadgeControllerTest {

    /**
     * Javadoc
     */
    @Autowired
    MockMvc mockMvc;

    /**
     * Javadoc
     */
    @MockBean
    BadgeRepository repository;

    /**
     * Javadoc
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Javadoc
     */
    List<Badge> badges = new ArrayList<>();

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BadgeControllerTest() { /* Default Constructor */ }

    /**
     * Javadoc
     */
    @BeforeEach
    void setUp() {
        badges = List.of(
                new Badge(1,"Test Name One", "Test Description One","Test Image One",null),
                new Badge(2,"Test Name Two", "Test Description Two","Test Image Two",null)
        );
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldFindAllBadges() throws Exception {
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

        when(repository.findAll()).thenReturn(badges);

        ResultActions resultActions = mockMvc.perform(get("/api/badges"))
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
    void shouldFindBadgeWhenGivenValidId() throws Exception {
        Badge badge = new Badge(1,"Test Title", "Test Body","Test Body",null);
        when(repository.findById(1)).thenReturn(Optional.of(badge));


        mockMvc.perform(get("/api/badges/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",
                        is(badge.name())))
                .andExpect(jsonPath("$.description",
                        is(badge.description())))
                .andExpect(jsonPath("$.image",
                        is(badge.image())));
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldCreateNewBadgeWhenGivenValidID() throws Exception {
        Badge badge = new Badge(1,"Test Title", "Test Body","Test Body",null);
        when(repository.save(badge)).thenReturn(badge);

        mockMvc.perform(post("/api/badges")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(badge)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                is(badge.name())))
                .andExpect(jsonPath("$.description",
                        is(badge.description())))
                .andExpect(jsonPath("$.image",
                        is(badge.image())));
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldUpdateBadgeWhenGivenValidBadge() throws Exception {
        Badge updated = new Badge(1,"Test Title", "Test Body","Test Body",null);
        when(repository.findById(1)).thenReturn(Optional.of(badges.get(0)));
        when(repository.save(updated)).thenReturn(updated);


        mockMvc.perform(put("/api/badges/1")
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
    void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidBadgeID() throws Exception {
        Badge updated = new Badge(1,"Test Title", "Test Body","Test Body",null);
        when(repository.save(updated)).thenReturn(updated);


        mockMvc.perform(put("/api/badges/999")
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
    void shouldDeleteBadgeWhenGivenValidID() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete("/api/badges/1"))
                .andExpect(status().isNoContent());

        verify(repository, times(1)).deleteById(1);
    }

}
