package com.coderdojo.zen.event;

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
@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
class EventControllerTest {

  /**
   * Javadoc
   */
  @Autowired
  MockMvc mockMvc;

  /**
   * Javadoc
   */
  @MockitoBean
  EventRepository repository;
  /**
   * Javadoc
   */
  List<Event> events = new ArrayList<>();
  /**
   * Javadoc
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  EventControllerTest() { /* Default Constructor */ }

  /**
   * Javadoc
   */
  @BeforeEach
  void setUp() {
    events = List.of(
        new Event(1, "Test Name One", "Test Description One", "Test Image One", null),
        new Event(2, "Test Name Two", "Test Description Two", "Test Image Two", null)
    );
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindAllEvents() throws Exception {
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

    when(repository.findAll()).thenReturn(events);

    ResultActions resultActions = mockMvc.perform(get("/api/events"))
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
  void shouldFindEventWhenGivenValidId() throws Exception {
    Event event = new Event(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(event));


    mockMvc.perform(get("/api/events/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name",
            is(event.name())))
        .andExpect(jsonPath("$.description",
            is(event.description())))
        .andExpect(jsonPath("$.image",
            is(event.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldCreateNewEventWhenGivenValidID() throws Exception {
    Event event = new Event(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(event)).thenReturn(event);

    mockMvc.perform(post("/api/events")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(event)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name",
            is(event.name())))
        .andExpect(jsonPath("$.description",
            is(event.description())))
        .andExpect(jsonPath("$.image",
            is(event.image())));
  }

  /**
   * Javadoc
   *
   * @throws Exception Example
   */
  @Test
  void shouldUpdateEventWhenGivenValidEvent() throws Exception {
    Event updated = new Event(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(events.get(0)));
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/events/1")
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
  void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidEventID() throws Exception {
    Event updated = new Event(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/events/999")
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
  void shouldDeleteEventWhenGivenValidID() throws Exception {
    doNothing().when(repository).deleteById(1);

    mockMvc.perform(delete("/api/events/1"))
        .andExpect(status().isNoContent());

    verify(repository, times(1)).deleteById(1);
  }

}
