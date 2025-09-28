package com.coderdojo.zen.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Javadoc.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  UserNotFoundException() { /* Default Constructor */
  }
}
