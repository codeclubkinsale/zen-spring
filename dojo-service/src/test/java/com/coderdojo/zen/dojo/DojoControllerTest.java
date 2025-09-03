package com.coderdojo.zen.dojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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
@WebMvcTest(DojoController.class)
@AutoConfigureMockMvc
class DojoControllerTest {

  /**
   * Javadoc
   */
  @Autowired
  MockMvc mockMvc;

  /**
   * Javadoc
   */
  @MockitoBean
  DojoRepository repository;
  /**
   * Javadoc
   */
  List<Dojo> dojos = new ArrayList<>();
  /**
   * Javadoc
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  DojoControllerTest() { /* Default Constructor */ }

  /**
   * Javadoc
   */
  @BeforeEach
  void setUp() {
    dojos = List.of(
        new Dojo(1, "Test Name One", "Test Description One", "Test Image One", null),
        new Dojo(2, "Test Name Two", "Test Description Two", "Test Image Two", null)
    );
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindAllDojos() throws Exception {
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

    when(repository.findAll()).thenReturn(dojos);

    ResultActions resultActions = mockMvc.perform(get("/api/dojos"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonResponse));

    JSONAssert.assertEquals(jsonResponse,
        resultActions.andReturn().getResponse().getContentAsString(), false);

  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindDojoWhenGivenValidId() throws Exception {
    Dojo dojo = new Dojo(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(dojo));


    mockMvc.perform(get("/api/dojos/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name",
            is(dojo.name())))
        .andExpect(jsonPath("$.description",
            is(dojo.description())))
        .andExpect(jsonPath("$.image",
            is(dojo.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldCreateNewDojoWhenGivenValidID() throws Exception {
    Dojo dojo = new Dojo(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(dojo)).thenReturn(dojo);

    mockMvc.perform(post("/api/dojos")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(dojo)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name",
            is(dojo.name())))
        .andExpect(jsonPath("$.description",
            is(dojo.description())))
        .andExpect(jsonPath("$.image",
            is(dojo.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldUpdateDojoWhenGivenValidDojo() throws Exception {
    Dojo updated = new Dojo(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(dojos.getFirst()));
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/dojos/1")
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
  void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidDojoID() throws Exception {
    Dojo updated = new Dojo(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/dojos/999")
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
  void shouldDeleteDojoWhenGivenValidID() throws Exception {
    doNothing().when(repository).deleteById(1);

    mockMvc.perform(delete("/api/dojos/1"))
        .andExpect(status().isNoContent());

    verify(repository, times(1)).deleteById(1);
  }

}
