package com.coderdojo.zen.event;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

/**
 * Javadoc
 */
interface EventRepository extends ListCrudRepository<Event,Integer> {

    Optional<Event> findByName(String name);

}
