package com.coderdojo.zen.award;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface AwardRepository extends ListCrudRepository<Award,Integer> {

    Optional<Award> findByName(String name);

}
