package com.coderdojo.zen.award.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Hero is the main entity we'll be using to . . .
 * Please see the class for true identity
 * @author Captain America
 */
@Entity
@Table(name = "awards")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "award_type")
    private AwardType awardType;

    @Column(name = "award_id")
    private String awardId;

}
