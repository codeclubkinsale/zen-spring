package com.coderdojo.zen.badge.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "badges", schema = "badge_service")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

}
