package com.coderdojo.zen.event;

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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    EventRepository eventRepository;

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    void setUp() {
        List<Event> events = List.of(new Event(1,"Test Title", "Test Body","Test Body",null));
        eventRepository.saveAll(events);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldReturnEventByName() {
        Event event = eventRepository.findByName("Test Title").orElseThrow();
        assertEquals("Test Title", event.name(), "Event title should be 'Hello, World!'");
    }

    @Test
    void shouldNotReturnEventWhenTitleIsNotFound() {
        Optional<Event> event = eventRepository.findByName("Hello, Wrong Title!");
        assertFalse(event.isPresent(), "Event should not be present");
    }

}
