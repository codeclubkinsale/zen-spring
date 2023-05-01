package com.coderdojo.zen.belt.dto;

import lombok.Builder;

@Builder
public record BeltResponse(String id, String name, String description, String imageURL) {
}
