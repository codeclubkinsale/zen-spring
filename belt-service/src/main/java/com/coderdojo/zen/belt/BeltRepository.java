package com.coderdojo.zen.belt;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface BeltRepository extends ListCrudRepository<Belt,Integer> {

    Optional<Belt> findByName(String name);

}
