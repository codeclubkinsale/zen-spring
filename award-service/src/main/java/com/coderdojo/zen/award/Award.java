package com.coderdojo.zen.award;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Javadoc.
 *
 * @param id          Example
 * @param name        Example
 * @param description Example
 * @param image       Example
 * @param version     Example
 */
record Award(
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
