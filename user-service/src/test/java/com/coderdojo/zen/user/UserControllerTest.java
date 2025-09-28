package com.coderdojo.zen.user;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

/**
 * Javadoc.
 */
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

  /**
   * Javadoc.
   */
  @Autowired
  MockMvc mockMvc;

  /**
   * Javadoc.
   */
  @MockitoBean
  UserRepository repository;
  /**
   * Javadoc.
   */
  List<User> users = new ArrayList<>();
  /**
   * Javadoc.
   */
  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  UserControllerTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @BeforeEach
  void setUp() {
    users = List.of(
        new User(1, "Test Name One", "Test Description One", "Test Image One", null),
        new User(2, "Test Name Two", "Test Description Two", "Test Image Two", null)
    );
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindAllUsers() throws Exception {
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

    when(repository.findAll()).thenReturn(users);

    ResultActions resultActions = mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonResponse));

    JSONAssert.assertEquals(jsonResponse,
        resultActions.andReturn().getResponse().getContentAsString(), false);

  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldFindUserWhenGivenValidId() throws Exception {
    User user = new User(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(user));


    mockMvc.perform(get("/api/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name",
            is(user.name())))
        .andExpect(jsonPath("$.description",
            is(user.description())))
        .andExpect(jsonPath("$.image",
            is(user.image())));
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldCreateNewUserWhenGivenValidId() throws Exception {
    User user = new User(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(user)).thenReturn(user);

    mockMvc.perform(post("/api/users")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name",
            is(user.name())))
        .andExpect(jsonPath("$.description",
            is(user.description())))
        .andExpect(jsonPath("$.image",
            is(user.image())));
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldUpdateUserWhenGivenValidUser() throws Exception {
    User updated = new User(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.findById(1)).thenReturn(Optional.of(users.getFirst()));
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/users/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updated)))
        .andExpect(status().isOk());
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldNotUpdateAndThrowNotFoundWhenGivenAnInvalidUserId() throws Exception {
    User updated = new User(1, "Test Title", "Test Body", "Test Body", null);
    when(repository.save(updated)).thenReturn(updated);


    mockMvc.perform(put("/api/users/999")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updated)))
        .andExpect(status().isNotFound());
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeleteUserWhenGivenValidId() throws Exception {
    doNothing().when(repository).deleteById(1);

    mockMvc.perform(delete("/api/users/1"))
        .andExpect(status().isNoContent());

    verify(repository, times(1)).deleteById(1);
  }

}
