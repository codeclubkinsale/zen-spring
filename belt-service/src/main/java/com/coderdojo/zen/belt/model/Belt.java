package com.coderdojo.zen.belt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Javadoc
 */
@Document(value = "belt")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Belt {

    @Id
    private Long id;

    private String name;

    private String description;

    private String imageURL;

}
