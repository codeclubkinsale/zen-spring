package com.coderdojo.zen.belt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Javadoc.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeltNotFoundException extends RuntimeException {

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  BeltNotFoundException() { /* Default Constructor */
  }
}
