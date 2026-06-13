package com.convivir.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.convivir.app")
public class ConvivirsmartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConvivirsmartApplication.class, args);
    }
}