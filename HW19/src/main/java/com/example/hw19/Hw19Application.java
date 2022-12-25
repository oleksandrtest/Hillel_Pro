package com.example.hw19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Hw19Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw19Application.class,args);

    }

}
