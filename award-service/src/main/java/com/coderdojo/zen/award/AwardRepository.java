package com.coderdojo.zen.award;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface AwardRepository extends ListCrudRepository<Award, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<Award> findByName(String name);

}
