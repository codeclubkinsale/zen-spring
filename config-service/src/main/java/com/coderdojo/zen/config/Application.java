package com.coderdojo.zen.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Javadoc
 */
@EnableConfigServer
@SpringBootApplication
public class Application {

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    Application() { /* Default Constructor */ }

    /**
     * Javadoc
     *
     * @param args Example
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}