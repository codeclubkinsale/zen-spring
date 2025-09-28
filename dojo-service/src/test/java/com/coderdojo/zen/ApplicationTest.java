package com.coderdojo.zen;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Javadoc.
 */
@TestConfiguration(proxyBeanMethods = false)
public class ApplicationTest {

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  ApplicationTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @return Example
   */
  @Bean
  @ServiceConnection
  PostgreSQLContainer<?> postgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
  }

  /**
   * Javadoc.
   *
   * @param args Example
   */
  public static void main(String[] args) {
    SpringApplication.from(Application::main).with(ApplicationTest.class).run(args);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void contextLoads() throws Exception {
    String[] args = {"main", "test"};
    ApplicationTest.main(args);
    assertNotNull(args);
  }

}
