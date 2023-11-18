package com.coderdojo.zen.belt.dto;

import lombok.Builder;

@Builder
public record BeltResponse(Long id, String name, String description, String imageURL) {
}
