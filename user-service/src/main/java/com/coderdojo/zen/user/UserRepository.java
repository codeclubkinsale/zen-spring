package com.coderdojo.zen.user;

import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Javadoc.
 */
interface UserRepository extends ListCrudRepository<User, Integer> {

  /**
   * Javadoc.
   *
   * @param name Example
   * @return Example
   */
  Optional<User> findByName(String name);

}
