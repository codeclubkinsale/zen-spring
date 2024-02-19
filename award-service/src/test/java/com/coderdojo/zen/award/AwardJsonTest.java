package com.coderdojo.zen.award;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@JsonTest
class AwardJsonTest {

    @Autowired
    private JacksonTester<Award> jacksonTester;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    AwardJsonTest() { }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldSerializeAward() throws Exception {
        Award award = new Award(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(award)).isEqualToJson(expected);
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeserializeAward() throws Exception {
        Award award = new Award(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(award.id());
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo(award.name());
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo(award.description());
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo(award.image());
    }
}

