package com.coderdojo.zen.belt;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Javadoc
 */
record Belt(
        @Id
        Integer id,
        @NotEmpty
        String name,
        @NotEmpty
        String description,
        @NotEmpty
        String image,
        @Version Integer version) {
}
