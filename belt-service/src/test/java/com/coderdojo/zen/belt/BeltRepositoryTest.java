package com.coderdojo.zen.belt;

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
public class BeltRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    BeltRepository beltRepository;

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    void setUp() {
        List<Belt> belts = List.of(new Belt(1,"Test Title", "Test Body","Test Body",null));
        beltRepository.saveAll(belts);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldReturnBeltByName() {
        Belt belt = beltRepository.findByName("Test Title").orElseThrow();
        assertEquals("Test Title", belt.name(), "Belt title should be 'Hello, World!'");
    }

    @Test
    void shouldNotReturnBeltWhenTitleIsNotFound() {
        Optional<Belt> belt = beltRepository.findByName("Hello, Wrong Title!");
        assertFalse(belt.isPresent(), "Belt should not be present");
    }

}
