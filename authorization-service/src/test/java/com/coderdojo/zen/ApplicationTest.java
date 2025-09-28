package com.coderdojo.zen;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;

/**
 * Javadoc.
 */
@TestConfiguration(proxyBeanMethods = false)
class ApplicationTest {

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.).
   */
  ApplicationTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   */
  @Test
  void testMain() {
    System.out.println("main");
    String[] args = {"main", "test"};

    final InputStream original = System.in;
    Application.main(args);
    System.setIn(original);
    assertNotNull(args);
  }
}
