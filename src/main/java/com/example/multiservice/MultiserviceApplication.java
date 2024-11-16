package com.example.multiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// @ComponentScan(basePackages = "com.example.multiservice.utils")

public class MultiserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiserviceApplication.class, args);
    }
}
