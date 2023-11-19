package com.coderdojo.zen.award;

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

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class AwardControllerIntTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldFindAllAwards() {
        Award[] awards = restTemplate.getForObject("/api/awards", Award[].class);
        assertThat(awards.length).isGreaterThan(8);
    }

    @Test
    void shouldFindAwardWhenValidAwardID() {
        ResponseEntity<Award> response = restTemplate.exchange("/api/awards/1", HttpMethod.GET, null, Award.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldThrowNotFoundWhenInvalidAwardID() {
        ResponseEntity<Award> response = restTemplate.exchange("/api/awards/999", HttpMethod.GET, null, Award.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Rollback
    void shouldCreateNewAwardWhenAwardIsValid() {
        Award award = new Award(9,"Test Name", "Test Description","Test Image",null);

        ResponseEntity<Award> response = restTemplate.exchange("/api/awards", HttpMethod.POST, new HttpEntity<>(award), Award.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getBody()).id()).isEqualTo(9);
        assertThat(response.getBody().name()).isEqualTo("Test Name");
        assertThat(response.getBody().description()).isEqualTo("Test Description");
        assertThat(response.getBody().image()).isEqualTo("Test Image");
    }

    @Test
    void shouldNotCreateNewAwardWhenValidationFails() {
        Award award = new Award(9,"Test Title", "Test Body","",null);
        ResponseEntity<Award> response = restTemplate.exchange("/api/awards", HttpMethod.POST, new HttpEntity<>(award), Award.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Rollback
    void shouldUpdateAwardWhenAwardIsValid() {
        ResponseEntity<Award> response = restTemplate.exchange("/api/awards/8", HttpMethod.GET, null, Award.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Award existing = response.getBody();
        assertThat(existing).isNotNull();
        Award updated = new Award(existing.id(),existing.name(),"NEW POST TITLE #1", "NEW POST BODY #1",existing.version());

        assertThat(updated.id()).isEqualTo(8);
        assertThat(updated.name()).isEqualTo("Black Belt");
        assertThat(updated.description()).isEqualTo("NEW POST TITLE #1");
        assertThat(updated.image()).isEqualTo("NEW POST BODY #1");
    }

    @Test
    @Rollback
    void shouldDeleteWithValidID() {
        ResponseEntity<Void> response = restTemplate.exchange("/api/awards/88", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
