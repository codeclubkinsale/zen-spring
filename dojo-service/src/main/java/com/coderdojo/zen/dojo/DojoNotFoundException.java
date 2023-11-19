package com.coderdojo.zen.dojo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Javadoc
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DojoNotFoundException extends RuntimeException {

}
