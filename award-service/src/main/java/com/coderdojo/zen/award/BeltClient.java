package com.coderdojo.zen.award;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BeltClient {
    private final RestClient restClient;

    public BeltClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://jsonplaceholderservice.com")
                .build();
    }

    public List<Award> findAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Award>>() {});
    }

}
