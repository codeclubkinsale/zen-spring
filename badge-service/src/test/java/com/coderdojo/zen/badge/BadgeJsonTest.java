package com.coderdojo.zen.badge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class BadgeJsonTest {

  @Autowired
  private JacksonTester<Badge> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BadgeJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeBadge() throws Exception {
    Badge badge = new Badge(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(badge)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeBadge() throws Exception {
    Badge badge = new Badge(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(badge.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(badge.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(badge.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(badge.image());
  }

}
