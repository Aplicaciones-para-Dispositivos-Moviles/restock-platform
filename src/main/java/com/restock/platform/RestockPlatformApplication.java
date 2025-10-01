package com.restock.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class RestockPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestockPlatformApplication.class, args);
    }

}
