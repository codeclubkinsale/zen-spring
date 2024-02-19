package com.coderdojo.zen.badge;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface BadgeRepository extends ListCrudRepository<Badge,Integer> {

    /**
     * Javadoc
     *
     * @param name Example
     * @return Example
     */
    Optional<Badge> findByName(String name);

}
