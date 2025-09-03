package com.coderdojo.zen.award;

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
@WebMvcTest(AwardController.class)
@AutoConfigureMockMvc
class AwardControllerTest {

  /**
   * Javadoc
   */
  @Autowired
  MockMvc mockMvc;

  /**
   * Javadoc
   */
  @MockitoBean
  AwardRepository repository;
  /**
   * Javadoc
   */
  List<Award> awards = new ArrayList<>();
  /**
   * Javadoc
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  AwardControllerTest() {
  }

  /**
   * Javadoc
   */
  @BeforeEach
  void setUp() {
    awards = List.of(
        new Award(1, "Test Name One", "Test Description One", "Test Image One", null),
        new Award(2, "Test Name Two", "Test Description Two", "Test Image Two", null)
    );
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindAllAwards() throws Exception {
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

    when(repository.findAll()).thenReturn(awards);

    ResultActions resultActions = mockMvc.perform(get("/api/awards"))
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
  void shouldFindAwardWhenGivenValidId() throws Exception {
    Award award = new Award(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(award));


    mockMvc.perform(get("/api/awards/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name",
            is(award.name())))
        .andExpect(jsonPath("$.description",
            is(award.description())))
        .andExpect(jsonPath("$.image",
            is(award.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldCreateNewAwardWhenGivenValidID() throws Exception {
    Award award = new Award(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(award)).thenReturn(award);

    mockMvc.perform(post("/api/awards")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(award)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name",
            is(award.name())))
        .andExpect(jsonPath("$.description",
            is(award.description())))
        .andExpect(jsonPath("$.image",
            is(award.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldUpdateAwardWhenGivenValidAward() throws Exception {
    Award updated = new Award(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(awards.getFirst()));
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/awards/1")
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
  void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidAwardID() throws Exception {
    Award updated = new Award(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/awards/999")
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
  void shouldDeleteAwardWhenGivenValidID() throws Exception {
    doNothing().when(repository).deleteById(1);

    mockMvc.perform(delete("/api/awards/1"))
        .andExpect(status().isNoContent());

    verify(repository, times(1)).deleteById(1);
  }

}
