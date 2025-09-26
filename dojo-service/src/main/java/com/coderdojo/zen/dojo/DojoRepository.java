package com.coderdojo.zen.dojo;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface DojoRepository extends ListCrudRepository<Dojo, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<Dojo> findByName(String name);

}
