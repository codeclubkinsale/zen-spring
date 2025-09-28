package com.coderdojo.zen.user;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Javadoc.
 *
 * @param id          Example
 * @param name        Example
 * @param description Example
 * @param image       Example
 * @param version     Example
 */
@Table(name = "users")
record User(
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
