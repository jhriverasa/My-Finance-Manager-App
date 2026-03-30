package com.myfinancemanager.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Main application entry point for MyFinanceManager API.
 * 
 * This Spring Boot application follows Hexagonal Architecture principles,
 * with clear separation between domain, application, and infrastructure layers.
 */
@SpringBootApplication
@EnableSpringDataWebSupport
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}