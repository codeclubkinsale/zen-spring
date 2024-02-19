package com.coderdojo.zen.award;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface AwardRepository extends ListCrudRepository<Award,Integer> {

    /**
     * Javadoc
     *
     * @param name Example
     * @return Example
     */
    Optional<Award> findByName(String name);

}
