package com.xm.poc.triggers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.xm.poc.triggers.db"})

public class TriggersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TriggersApplication.class, args);
    }

}
