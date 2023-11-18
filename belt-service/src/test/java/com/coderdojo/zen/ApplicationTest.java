package com.coderdojo.zen;

import org.springframework.boot.SpringApplication;

public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(ApplicationConfiguration.class)
                .run(args);
    }

}
