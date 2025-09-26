package com.coderdojo.zen.event;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface EventRepository extends ListCrudRepository<Event, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<Event> findByName(String name);

}
