package com.coderdojo.zen.dojo;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Javadoc
 */
record Dojo(
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
