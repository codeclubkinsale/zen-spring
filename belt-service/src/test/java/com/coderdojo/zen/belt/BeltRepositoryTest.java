package com.coderdojo.zen.belt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Javadoc.
 */
@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BeltRepositoryTest {

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
  BeltRepository beltRepository;

  /**
   * Javadoc.
   */
  @Autowired
  JdbcConnectionDetails jdbcConnectionDetails;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BeltRepositoryTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @BeforeEach
  void setUp() {
    List<Belt> belts = List.of(new Belt(9, "Test Title", "Test Body", "Test Body", null));
    beltRepository.saveAll(belts);
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
  void shouldReturnBeltByName() {
    Belt belt = beltRepository.findByName("Test Title").orElseThrow();
    assertEquals("Test Title", belt.name(), "Belt title should be 'Hello, World!'");
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldNotReturnBeltWhenTitleIsNotFound() {
    Optional<Belt> belt = beltRepository.findByName("Hello, Wrong Title!");
    assertFalse(belt.isPresent(), "Belt should not be present");
  }

}
