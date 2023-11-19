package com.coderdojo.zen.award;

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
public class AwardRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    AwardRepository awardRepository;

    @Autowired
    JdbcConnectionDetails jdbcConnectionDetails;

    @BeforeEach
    void setUp() {
        List<Award> awards = List.of(new Award(1,"Test Title", "Test Body","Test Body",null));
        awardRepository.saveAll(awards);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldReturnAwardByName() {
        Award award = awardRepository.findByName("Test Title").orElseThrow();
        assertEquals("Test Title", award.name(), "Award title should be 'Hello, World!'");
    }

    @Test
    void shouldNotReturnAwardWhenTitleIsNotFound() {
        Optional<Award> award = awardRepository.findByName("Hello, Wrong Title!");
        assertFalse(award.isPresent(), "Award should not be present");
    }

}
