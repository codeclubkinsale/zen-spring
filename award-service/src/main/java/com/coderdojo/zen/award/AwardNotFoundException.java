package com.coderdojo.zen.award;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Javadoc.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AwardNotFoundException extends RuntimeException {

  /**
   * Sole constructor. (For invocation by subclass
   * constructors, typically implicit.)
   */
  AwardNotFoundException() { /* Default Constructor */
  }
}
