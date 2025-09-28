package com.coderdojo.zen.award;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

/**
 * Javadoc.
 */
@JsonTest
class AwardJsonTest {

  @Autowired
  private JacksonTester<Award> jacksonTester;

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  AwardJsonTest() { /* Default Constructor */
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldSerializeAward() throws Exception {
    Award award = new Award(1, "Name", "Description", "Image", null);
    String expected = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;
    assertThat(jacksonTester.write(award)).isEqualToJson(expected);
  }

  /**
   * Javadoc.
   *
   * @throws Exception Example
   */
  @Test
  void shouldDeserializeAward() throws Exception {
    Award award = new Award(1, "Name", "Description", "Image", null);
    String content = """
        {"id":1,"name":"Name","description":"Description","image":"Image","version":null}
        """;

    assertThat(jacksonTester.parseObject(content).id()).isEqualTo(award.id());
    assertThat(jacksonTester.parseObject(content).name()).isEqualTo(award.name());
    assertThat(jacksonTester.parseObject(content).description()).isEqualTo(award.description());
    assertThat(jacksonTester.parseObject(content).image()).isEqualTo(award.image());
  }

}
