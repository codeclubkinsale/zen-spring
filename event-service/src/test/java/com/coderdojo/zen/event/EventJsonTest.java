package com.coderdojo.zen.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@JsonTest
class EventJsonTest {

    /**
     * Javadoc
     */
    @Autowired
    private JacksonTester<Event> jacksonTester;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    EventJsonTest() { /* Default Constructor */ }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldSerializeEvent() throws Exception {
        Event event = new Event(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(event)).isEqualToJson(expected);
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeserializeEvent() throws Exception {
        Event event = new Event(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(event.id());
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo(event.name());
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo(event.description());
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo(event.image());
    }

}
