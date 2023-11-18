package com.coderdojo.zen.belt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BeltJsonTest {

    @Autowired
    private JacksonTester<Belt> jacksonTester;

    @Test
    void shouldSerializeBelt() throws Exception {
        Belt belt = new Belt(1,"Two", "Three","Four");
        String expected = STR."""
                {
                    "id":\{belt.id()},
                    "name":"\{belt.name()}",
                    "description":"\{belt.description()}",
                    "imageURL":"\{belt.imageURL()}"
                }
                """;
        assertThat(jacksonTester.write(belt)).isEqualToJson(expected);
    }

    @Test
    void shouldDeserializeBelt() throws Exception {
        Belt belt = new Belt(1,"Two", "Three","Four");
        String content = STR."""
                {
                    "id":\{belt.id()},
                    "name":"\{belt.name()}",
                    "description":"\{belt.description()}",
                    "imageURL":"\{belt.imageURL()}"
                }
                """;

        assertThat(jacksonTester.parseObject(content).id()).isEqualTo(1);
        assertThat(jacksonTester.parseObject(content).name()).isEqualTo("Two");
        assertThat(jacksonTester.parseObject(content).description()).isEqualTo("Three");
        assertThat(jacksonTester.parseObject(content).imageURL()).isEqualTo("Four");
    }

}
