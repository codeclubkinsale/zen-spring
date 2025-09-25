package com.coderdojo.zen.belt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@JsonTest
class BeltJsonTest {

    /**
     * Javadoc
     */
    @Autowired
    private JacksonTester<Belt> jacksonTester;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BeltJsonTest() { /* Default Constructor */ }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldSerializeBelt() throws Exception {
        Belt belt = new Belt(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(belt)).isEqualToJson(expected);
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeserializeBelt() throws Exception {
        Belt belt = new Belt(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(belt.id());
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo(belt.name());
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo(belt.description());
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo(belt.image());
    }

}
