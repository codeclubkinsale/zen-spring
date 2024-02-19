package com.coderdojo.zen.badge;

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

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BadgeControllerIntTest {

    /**
     * Javadoc
     */
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    /**
     * Javadoc
     */
    @Autowired
    TestRestTemplate restTemplate;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BadgeControllerIntTest() { /* Default Constructor */ }

    /**
     * Javadoc
     */
    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    /**
     * Javadoc
     */
    @Test
    void shouldFindAllBadges() {
        Badge[] badges = restTemplate.getForObject("/api/badges", Badge[].class);
        assertThat(badges).hasSizeGreaterThan(8);
    }

    /**
     * Javadoc
     */
    @Test
    void shouldFindBadgeWhenValidBadgeID() {
        ResponseEntity<Badge> response = restTemplate.exchange("/api/badges/1", HttpMethod.GET, null, Badge.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    /**
     * Javadoc
     */
    @Test
    void shouldThrowNotFoundWhenInvalidBadgeID() {
        ResponseEntity<Badge> response = restTemplate.exchange("/api/badges/999", HttpMethod.GET, null, Badge.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Javadoc
     */
    @Test
    @Rollback
    void shouldCreateNewBadgeWhenBadgeIsValid() {
        Badge badge = new Badge(9,"Test Name", "Test Description","Test Image",null);

        ResponseEntity<Badge> response = restTemplate.exchange("/api/badges", HttpMethod.POST, new HttpEntity<>(badge), Badge.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).id()).isEqualTo(9);
        assertThat(response.getBody().name()).isEqualTo("Test Name");
        assertThat(response.getBody().description()).isEqualTo("Test Description");
        assertThat(response.getBody().image()).isEqualTo("Test Image");
    }

    /**
     * Javadoc
     */
    @Test
    void shouldNotCreateNewBadgeWhenValidationFails() {
        Badge badge = new Badge(9,"Test Title", "Test Body","",null);
        ResponseEntity<Badge> response = restTemplate.exchange("/api/badges", HttpMethod.POST, new HttpEntity<>(badge), Badge.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Javadoc
     */
    @Test
    @Rollback
    void shouldUpdateBadgeWhenBadgeIsValid() {
        ResponseEntity<Badge> response = restTemplate.exchange("/api/badges/8", HttpMethod.GET, null, Badge.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Badge existing = response.getBody();
        assertThat(existing).isNotNull();
        Badge updated = new Badge(existing.id(),existing.name(),"NEW POST TITLE #1", "NEW POST BODY #1",existing.version());

        assertThat(updated.id()).isEqualTo(8);
        assertThat(updated.name()).isEqualTo("Black Belt");
        assertThat(updated.description()).isEqualTo("NEW POST TITLE #1");
        assertThat(updated.image()).isEqualTo("NEW POST BODY #1");
    }

    /**
     * Javadoc
     */
    @Test
    @Rollback
    void shouldDeleteWithValidID() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/badges/88", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
