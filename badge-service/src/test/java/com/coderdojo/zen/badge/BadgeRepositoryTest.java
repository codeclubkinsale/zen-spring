package com.coderdojo.zen.badge;

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
class BadgeRepositoryTest {

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
  BadgeRepository badgeRepository;

  /**
   * Javadoc.
   */
  @Autowired
  JdbcConnectionDetails jdbcConnectionDetails;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BadgeRepositoryTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @BeforeEach
  void setUp() {
    List<Badge> badges = List.of(new Badge(9, "Test Title", "Test Body", "Test Body", null));
    badgeRepository.saveAll(badges);
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
  void shouldReturnBadgeByName() {
    Badge badge = badgeRepository.findByName("Test Title").orElseThrow();
    assertEquals("Test Title", badge.name(), "Badge title should be 'Hello, World!'");
  }

  /**
   * Javadoc.
   */
  @Test
  void shouldNotReturnBadgeWhenTitleIsNotFound() {
    Optional<Badge> badge = badgeRepository.findByName("Hello, Wrong Title!");
    assertFalse(badge.isPresent(), "Badge should not be present");
  }

}
