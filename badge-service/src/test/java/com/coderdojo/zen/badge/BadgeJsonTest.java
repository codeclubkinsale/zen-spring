package com.coderdojo.zen.badge;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BadgeJsonTest {

    @Autowired
    private JacksonTester<Badge> jacksonTester;

    @Test
    void shouldSerializeBadge() throws Exception {
        Badge badge = new Badge(1,"Test Title", "Test Body","Test Body",null);
        String expected = """
                {"id":1,"name":"Test Title","description":"Test Body","image":"Test Body","version":null}
        """;
        assertThat(jacksonTester.write(badge)).isEqualToJson(expected);
    }

    @Test
    void shouldDeserializeBadge() throws Exception {
        Badge badge = new Badge(1,"Test Name", "Test Description","Test Image",null);
        String content = """
                {"id":1,"name":"Test Name","description":"Test Description","image":"Test Image","version":null}
        """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(1);
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo("Test Name");
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo("Test Description");
        assertThat(jacksonTester.parseObject(content).image()).isEqualTo("Test Image");
    }

}
