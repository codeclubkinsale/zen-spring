package com.coderdojo.zen.dojo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@JsonTest
class DojoJsonTest {

    @Autowired
    private JacksonTester<Dojo> jacksonTester;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    DojoJsonTest() { /* Default Constructor */ }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldSerializeDojo() throws Exception {
        Dojo dojo = new Dojo(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(dojo)).isEqualToJson(expected);
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeserializeDojo() throws Exception {
        Dojo dojo = new Dojo(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(dojo.id());
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo(dojo.name());
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo(dojo.description());
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo(dojo.image());
    }

}
