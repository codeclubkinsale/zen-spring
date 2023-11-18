package com.coderdojo.zen.belt;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

/**
 * Javadoc
 */
record Belt (

    @Id
    Integer id,
    @NotEmpty
    String name,
    @NotEmpty
    String description,
    @Column("image_url")
    String imageURL){

}
