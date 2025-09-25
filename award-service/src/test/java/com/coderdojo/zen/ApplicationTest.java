package com.coderdojo.zen;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Javadoc
 */
@TestConfiguration(proxyBeanMethods = false)
public class ApplicationTest {

	/**
	 * Javadoc
	 *
	 * @return Example
	 */
	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
	}

	/**
	 * Javadoc
	 *
	 * @param args Example
	 */
	public static void main(String[] args) {
		SpringApplication.from(Application::main).with(ApplicationTest.class).run(args);
	}

	/**
	 * Sole constructor. (For invocation by subclass
	 * constructors, typically implicit.)
	 */
	ApplicationTest() { }

	/**
	 * Javadoc
	 *
	 * @throws Exception Example
	 */
	@Test
	void contextLoads() throws Exception {
      // Empty test that would fail if our Spring configuration does not load correctly.
	}

}
