package com.coderdojo.zen.user;

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
class UserRepositoryTest {

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
  UserRepository userRepository;

  /**
   * Javadoc.
   */
  @Autowired
  JdbcConnectionDetails jdbcConnectionDetails;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  UserRepositoryTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @BeforeEach
  void setUp() {
    List<User> users = List.of(new User(9, "Test Title", "Test Body", "Test Body", null));
    userRepository.saveAll(users);
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
  void shouldReturnUserByName() {
    User user = userRepository.findByName("Test Title").orElseThrow();
    assertEquals("Test Title", user.name(), "User title should be 'Hello, World!'");
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldNotReturnUserWhenTitleIsNotFound() {
    Optional<User> user = userRepository.findByName("Hello, Wrong Title!");
    assertFalse(user.isPresent(), "User should not be present");
  }

}
