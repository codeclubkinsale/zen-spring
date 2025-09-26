package com.coderdojo.zen.badge;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface BadgeRepository extends ListCrudRepository<Badge, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<Badge> findByName(String name);

}
