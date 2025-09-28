package com.coderdojo.zen.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class EventJsonTest {

  @Autowired
  private JacksonTester<Event> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  EventJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeEvent() throws Exception {
    Event event = new Event(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(event)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeEvent() throws Exception {
    Event event = new Event(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(event.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(event.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(event.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(event.image());
  }

}
