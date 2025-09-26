package com.coderdojo.zen.belt;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface BeltRepository extends ListCrudRepository<Belt, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<Belt> findByName(String name);

}
