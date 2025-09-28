package com.coderdojo.zen;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

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
   * @param args Example
   */
  public static void main(String[] args) {
    SpringApplication.from(Application::main).with(ApplicationTest.class).run(args);
  }

  /**
   * Javadoc.
   */
  @Test
  void contextLoads() {
    String[] args = {"main", "test"};
    ApplicationTest.main(args);
    assertNotNull(args);
  }

}
