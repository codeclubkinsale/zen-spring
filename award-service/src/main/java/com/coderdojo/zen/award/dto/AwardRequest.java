package com.coderdojo.zen.award.dto;

import lombok.Builder;

@Builder
public record AwardRequest(String description, String awardType, String awardId ) {
}
