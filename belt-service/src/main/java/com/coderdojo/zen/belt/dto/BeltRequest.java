package com.coderdojo.zen.belt.dto;

import lombok.Builder;


@Builder
public record BeltRequest(String name, String description, String imageURL) {

}
