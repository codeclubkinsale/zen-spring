package com.coderdojo.zen.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class UserJsonTest {

  @Autowired
  private JacksonTester<User> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  UserJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeUser() throws Exception {
    User user = new User(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(user)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeUser() throws Exception {
    User user = new User(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(user.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(user.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(user.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(user.image());
  }

}
