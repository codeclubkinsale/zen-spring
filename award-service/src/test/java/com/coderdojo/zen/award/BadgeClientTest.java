package com.coderdojo.zen.award;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(BadgeClient.class)
class BadgeClientTest {

    @Autowired
    MockRestServiceServer server;

    @Autowired
    BadgeClient badgeClient;

    @Autowired
    ObjectMapper objectMapper;

    @Test
   void shouldReturnAllPosts() throws JsonProcessingException {
        // given
        List<Award> data = List.of(
                new Award(1, "w", "Hello, World!", "This is my first post!", 2),
                new Award(2, "1", "Testing Rest Client with @RestClientTest", "This is a post about testing RestClient calls", 3)
        );

        // when
        this.server
                .expect(requestTo("https://jsonplaceholder.typicode.com/posts"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(data), MediaType.APPLICATION_JSON));

        // then
        List<Award> posts = badgeClient.findAll();
        assertThat(posts).hasSize(2);
    }

}