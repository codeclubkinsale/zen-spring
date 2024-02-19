package com.coderdojo.zen.dojo;

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
class DojoControllerIntTest {

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
    DojoControllerIntTest() { /* Default Constructor */ }

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
    void shouldFindAllDojos() {
        Dojo[] dojos = restTemplate.getForObject("/api/dojos", Dojo[].class);
        assertThat(dojos).hasSizeGreaterThan(8);
    }

    /**
     * Javadoc
     */
    @Test
    void shouldFindDojoWhenValidDojoID() {
        ResponseEntity<Dojo> response = restTemplate.exchange("/api/dojos/1", HttpMethod.GET, null, Dojo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    /**
     * Javadoc
     */
    @Test
    void shouldThrowNotFoundWhenInvalidDojoID() {
        ResponseEntity<Dojo> response = restTemplate.exchange("/api/dojos/999", HttpMethod.GET, null, Dojo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Javadoc
     */
    @Test
    @Rollback
    void shouldCreateNewDojoWhenDojoIsValid() {
        Dojo dojo = new Dojo(9,"Test Name", "Test Description","Test Image",null);

        ResponseEntity<Dojo> response = restTemplate.exchange("/api/dojos", HttpMethod.POST, new HttpEntity<>(dojo), Dojo.class);
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
    void shouldNotCreateNewDojoWhenValidationFails() {
        Dojo dojo = new Dojo(9,"Test Title", "Test Body","",null);
        ResponseEntity<Dojo> response = restTemplate.exchange("/api/dojos", HttpMethod.POST, new HttpEntity<>(dojo), Dojo.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    /**
     * Javadoc
     */
    @Test
    @Rollback
    void shouldUpdateDojoWhenDojoIsValid() {
        ResponseEntity<Dojo> response = restTemplate.exchange("/api/dojos/8", HttpMethod.GET, null, Dojo.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Dojo existing = response.getBody();
        assertThat(existing).isNotNull();
        Dojo updated = new Dojo(existing.id(),existing.name(),"NEW POST TITLE #1", "NEW POST BODY #1",existing.version());

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
        ResponseEntity<Void> response = restTemplate.exchange("/api/dojos/88", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
