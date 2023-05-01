package com.coderdojo.zen.award.dto;

import lombok.Builder;

@Builder
public record AwardResponse(Long id, String description, String awardType, String awardId) {
}
