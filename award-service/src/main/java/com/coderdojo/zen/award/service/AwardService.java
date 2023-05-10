package com.coderdojo.zen.award.service;

import com.coderdojo.zen.award.dto.AwardRequest;
import com.coderdojo.zen.award.dto.AwardResponse;
import com.coderdojo.zen.award.dto.Badge;
import com.coderdojo.zen.award.model.Award;
import com.coderdojo.zen.award.model.AwardType;
import com.coderdojo.zen.award.repository.AwardRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AwardService {
    private final AwardRepository awardRepository;
    private final ObservationRegistry observationRegistry;
    private final WebClient webClient;
    /**
     * The public name of a hero that is common knowledge
     */
    public List<AwardResponse> getAllAwards() {
        List<Award> awards = awardRepository.findAll();

        return awards.stream().map(this::mapToAwardResponse).toList();
    }
    public String createAward(AwardRequest awardRequest) {
        Award award = Award.builder()
                .description(awardRequest.description())
                .awardType(AwardType.valueOf(awardRequest.awardType()))
                .awardId(awardRequest.awardId())
                .build();

        Observation inventoryServiceObservation = Observation.createNotStarted("inventory-service-lookup",
                this.observationRegistry);
        inventoryServiceObservation.lowCardinalityKeyValue("call", "inventory-service");
        return inventoryServiceObservation.observe(() -> {
            Badge badge = webClient.get()
                    .uri("http://localhost:8082/api/badges/1")
                    .retrieve()
                    .bodyToMono(Badge.class)
                    .block();

            if (badge != null) {
                awardRepository.save(award);
                return "Award created successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        });
    }

    private AwardResponse mapToAwardResponse(Award award) {
        return AwardResponse.builder()
                .id(award.getId())
                .description(award.getDescription())
                .awardType(String.valueOf(award.getAwardType()))
                .awardId(award.getAwardId())
                .build();
    }
}
