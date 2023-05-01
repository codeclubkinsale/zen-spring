package com.coderdojo.zen.award.service;

import com.coderdojo.zen.award.dto.AwardRequest;
import com.coderdojo.zen.award.dto.AwardResponse;
import com.coderdojo.zen.award.dto.Badge;
import com.coderdojo.zen.award.model.Award;
import com.coderdojo.zen.award.model.AwardType;
import com.coderdojo.zen.award.repository.AwardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AwardService {
    private final AwardRepository awardRepository;
    private final WebClient webClient;
    /**
     * The public name of a hero that is common knowledge
     */
    public List<AwardResponse> getAllAwards() {
        List<Award> awards = awardRepository.findAll();

        return awards.stream().map(this::mapToAwardResponse).toList();
    }

    public void createAward(AwardRequest awardRequest) {
        Award award = Award.builder()
                .description(awardRequest.description())
                .awardType(AwardType.valueOf(awardRequest.awardType()))
                .awardId(awardRequest.awardId())
                .build();

        Badge badge = webClient.get()
                .uri("http://localhost:8082/api/badges/1")
                .retrieve()
                .bodyToMono(Badge.class)
                .block();
        if (badge != null) {
            awardRepository.save(award);
        }
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
