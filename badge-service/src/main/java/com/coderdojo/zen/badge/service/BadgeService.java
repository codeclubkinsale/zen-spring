package com.coderdojo.zen.badge.service;

import com.coderdojo.zen.badge.dto.BadgeRequest;
import com.coderdojo.zen.badge.dto.BadgeResponse;
import com.coderdojo.zen.badge.model.Badge;
import com.coderdojo.zen.badge.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    
    /**
     * The public name of a hero that is common knowledge
     */
    public List<BadgeResponse> getAllBadges() {
        List<Badge> badges = badgeRepository.findAll();

        return badges.stream().map(this::mapToBadgeResponse).toList();
    }

    public void createBadge(BadgeRequest badgeRequest) {
        Badge badge = Badge.builder()
                .description(badgeRequest.description())
                .category(badgeRequest.category())
                .build();

        badgeRepository.save(badge);
    }
    public BadgeResponse getBadgeById(Long id) {
        Optional<Badge> optionalBadge = badgeRepository.findById(id);
        if (optionalBadge.isPresent()) {
            Badge badge = optionalBadge.get();
            return BadgeResponse.builder()
                    .id(badge.getId())
                    .name(badge.getName())
                    .description(badge.getDescription())
                    .category(badge.getCategory())
                    .imageURL(badge.getImageURl())
                    .build();
        } else {
            return null;
        }
    }
    

    private BadgeResponse mapToBadgeResponse(Badge badge) {
        return BadgeResponse.builder()
                .id(badge.getId())
                .description(badge.getDescription())
                .category(badge.getCategory())
                .build();
    }
}
