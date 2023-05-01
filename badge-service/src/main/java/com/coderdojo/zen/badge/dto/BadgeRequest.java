package com.coderdojo.zen.badge.dto;

import com.coderdojo.zen.badge.model.Category;

public record BadgeRequest(String name, String description, Category category) {
}
