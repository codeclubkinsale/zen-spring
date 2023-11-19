package com.coderdojo.zen.badge;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface BadgeRepository extends ListCrudRepository<Badge,Integer> {

    Optional<Badge> findByName(String name);

}
