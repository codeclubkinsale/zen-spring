package com.coderdojo.zen.belt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class BeltJsonTest {

  @Autowired
  private JacksonTester<Belt> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BeltJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeBelt() throws Exception {
    Belt belt = new Belt(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(belt)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeBelt() throws Exception {
    Belt belt = new Belt(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(belt.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(belt.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(belt.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(belt.image());
  }

}
