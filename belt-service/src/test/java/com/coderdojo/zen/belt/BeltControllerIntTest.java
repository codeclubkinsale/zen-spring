package com.coderdojo.zen.belt;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Javadoc.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BeltControllerIntTest {

  /**
   * Javadoc.
   */
  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

  /**
   * Javadoc.
   */
  @Autowired
  TestRestTemplate restTemplate;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BeltControllerIntTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @Test
  void connectionEstablished() {
    assertThat(postgres.isCreated()).isTrue();
    assertThat(postgres.isRunning()).isTrue();
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldFindAllBelts() {
    Belt[] belts = restTemplate.getForObject("/api/belts", Belt[].class);
    assertThat(belts).hasSizeGreaterThan(7);
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldFindBeltWhenValidBeltId() {
    ResponseEntity<Belt> response =
        restTemplate.exchange("/api/belts/1", HttpMethod.GET, null, Belt.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldThrowNotFoundWhenInvalidBeltId() {
    ResponseEntity<Belt> response =
        restTemplate.exchange("/api/belts/999", HttpMethod.GET, null, Belt.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  /**
   * Javadoc.
   */
  @Test
  @Rollback
  void shouldCreateNewBeltWhenBeltIsValid() {
    Belt belt = new Belt(9, "Test Name", "Test Description", "Test Image", null);

    ResponseEntity<Belt> response =
        restTemplate.exchange("/api/belts", HttpMethod.POST, new HttpEntity<>(belt), Belt.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(Objects.requireNonNull(response.getBody()).id()).isEqualTo(9);
    assertThat(response.getBody().name()).isEqualTo("Test Name");
    assertThat(response.getBody().description()).isEqualTo("Test Description");
    assertThat(response.getBody().image()).isEqualTo("Test Image");
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldNotCreateNewBeltWhenValidationFails() {
    Belt belt = new Belt(9, "Test Title", "Test Body", "", null);
    ResponseEntity<Belt> response =
        restTemplate.exchange("/api/belts", HttpMethod.POST, new HttpEntity<>(belt), Belt.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  /**
   * Javadoc.
   */
  @Test
  @Rollback
  void shouldUpdateBeltWhenBeltIsValid() {
    ResponseEntity<Belt> response =
        restTemplate.exchange("/api/belts/8", HttpMethod.GET, null, Belt.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    Belt existing = response.getBody();
    assertThat(existing).isNotNull();
    Belt updated =
        new Belt(existing.id(), existing.name(), "NEW POST TITLE #1", "NEW POST BODY #1",
            existing.version());

    assertThat(updated.id()).isEqualTo(8);
    assertThat(updated.name()).isEqualTo("excepturi optio8");
    assertThat(updated.description()).isEqualTo("NEW POST TITLE #1");
    assertThat(updated.image()).isEqualTo("NEW POST BODY #1");
  }

  /**
   * Javadoc.
   */
  @Test
  @Rollback
  void shouldDeleteWithValidId() {
    ResponseEntity<Void> response =
        restTemplate.exchange("/api/belts/88", HttpMethod.DELETE, null, Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

}
