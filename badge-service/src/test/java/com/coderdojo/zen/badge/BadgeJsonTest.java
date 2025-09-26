package com.coderdojo.zen.badge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Javadoc
 */
@JsonTest
class BadgeJsonTest {

    @Autowired
    private JacksonTester<Badge> jacksonTester;

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BadgeJsonTest() { /* Default Constructor */ }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldSerializeBadge() throws Exception {
        Badge badge = new Badge(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(badge)).isEqualToJson(expected);
    }

    /**
     * Javadoc
     *
     * @throws Exception Example
     */
    @Test
    void shouldDeserializeBadge() throws Exception {
        Badge badge = new Badge(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(badge.id());
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo(badge.name());
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo(badge.description());
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo(badge.image());
    }

}
