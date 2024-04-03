package com.coderdojo.zen.award;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class BadgeClient {
    private final RestClient restClient;

    @Autowired
    private EurekaClient discoveryClient;

    List<ServiceInstance> instances = discoveryClient.getInstancesById("badge-service");

    public BadgeClient(RestClient.Builder builder) {
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
