package com.coderdojo.zen.dojo;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface DojoRepository extends ListCrudRepository<Dojo,Integer> {

    /**
     * Javadoc
     */
    Optional<Dojo> findByName(String name);

}
