package com.coderdojo.zen.belt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeltController.class)
@AutoConfigureMockMvc
class BeltControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeltRepository repository;

    List<Belt> belts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        belts = List.of(
                new Belt(1,"Hello, World!", "This is my first belt.",null),
                new Belt(2,"Second Belt", "This is my second belt.",null)
        );
    }

    @Test
    void shouldFindAllBelts() throws Exception {
        String jsonResponse = """
                [
                    {
                        "id":1,
                        "name":"Hello, World!",
                        "description":"This is my first belt.",
                        "imageURL": null
                    },
                    {
                        "id":2,
                        "name":"Second Belt",
                        "description":"This is my second belt.",
                        "imageURL": null
                    }
                ]
                """;

        when(repository.findAll()).thenReturn(belts);

        ResultActions resultActions = mockMvc.perform(get("/api/belts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        JSONAssert.assertEquals(jsonResponse, resultActions.andReturn().getResponse().getContentAsString(), false);

    }

    @Test
    void shouldFindBeltWhenGivenValidId() throws Exception {
        Belt belt = new Belt(1,"Test Title", "Test Body",null);
        when(repository.findById(1)).thenReturn(Optional.of(belt));
        String json = STR."""
                {
                    "id":\{belt.id()},
                    "name":"\{belt.name()}",
                    "description":"\{belt.description()}",
                    "imageURL": null
                }
                """;

        mockMvc.perform(get("/api/belts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void shouldCreateNewBeltWhenGivenValidID() throws Exception {
        Belt belt = new Belt(3,"This is my brand new belt", "TEST BODY",null);
        when(repository.save(belt)).thenReturn(belt);
        String json = STR."""
                {
                    "id":\{belt.id()},
                    "name":"\{belt.name()}",
                    "description":"\{belt.description()}",
                    "imageURL": null
                }
                """;

        mockMvc.perform(post("/api/belts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(json));
    }

    @Test
    void shouldUpdateBeltWhenGivenValidBelt() throws Exception {
        Belt updated = new Belt(1,"This is my brand new belt", "UPDATED BODY","");
        when(repository.findById(1)).thenReturn(Optional.of(belts.get(0)));
        when(repository.save(updated)).thenReturn(updated);
        String requestBody = STR."""
                {
                    "id":\{updated.id()},
                    "name":"\{updated.name()}",
                    "description":"\{updated.description()}",
                    "imageURL":"\{updated.imageURL()}"
                }
                """;

        mockMvc.perform(put("/api/belts/1")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidBeltID() throws Exception {
        Belt updated = new Belt(50,"This is my brand new belt", "UPDATED BODY","");
        when(repository.save(updated)).thenReturn(updated);
        String json = STR."""
                {
                    "id":\{updated.id()},
                    "name":"\{updated.name()}",
                    "description":"\{updated.description()}",
                    "imageURL":"\{updated.imageURL()}"
                }
                """;

        mockMvc.perform(put("/api/belts/999")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteBeltWhenGivenValidID() throws Exception {
        doNothing().when(repository).deleteById(1);

        mockMvc.perform(delete("/api/belts/1"))
                .andExpect(status().isNoContent());

        verify(repository, times(1)).deleteById(1);
    }

}
