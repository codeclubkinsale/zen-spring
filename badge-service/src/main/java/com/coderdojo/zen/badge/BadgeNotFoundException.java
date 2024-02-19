package com.coderdojo.zen.badge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Javadoc
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadgeNotFoundException extends RuntimeException {

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    BadgeNotFoundException() { /* Default Constructor */ }
}
