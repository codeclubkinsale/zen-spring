package com.coderdojo.zen.dojo;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface DojoRepository extends ListCrudRepository<Dojo,Integer> {

    /**
     * Javadoc
     *
     * @param name Example
     * @return Example
     */
    Optional<Dojo> findByName(String name);

}
