package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties
@EnableTransactionManagement
public class Hw19Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw19Application.class,args);

    }

}
