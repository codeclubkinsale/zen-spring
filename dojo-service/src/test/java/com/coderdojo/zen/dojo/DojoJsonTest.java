package com.coderdojo.zen.dojo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class DojoJsonTest {

  @Autowired
  private JacksonTester<Dojo> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  DojoJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeDojo() throws Exception {
    Dojo dojo = new Dojo(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(dojo)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeDojo() throws Exception {
    Dojo dojo = new Dojo(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(dojo.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(dojo.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(dojo.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(dojo.image());
  }

}
