package com.coderdojo.zen.belt;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface BeltRepository extends ListCrudRepository<Belt,Integer> {

    /**
     * Javadoc
     *
     * @param name Example
     * @return Example
     */
    Optional<Belt> findByName(String name);

}
