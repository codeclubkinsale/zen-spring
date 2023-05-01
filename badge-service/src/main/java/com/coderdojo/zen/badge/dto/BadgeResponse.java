package com.coderdojo.zen.badge.dto;

import com.coderdojo.zen.badge.model.Category;
import lombok.Builder;

@Builder
public record BadgeResponse(Long id, String name, String description, Category category) {
}
