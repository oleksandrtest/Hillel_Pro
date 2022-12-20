package com.example.hw18;

import com.example.hw18.model.Car;
import com.example.hw18.model.Owner;
import com.example.hw18.service.OwnerService;
import com.example.hw18.service.impl.OwnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Hw18Application {


    public static void main(String[] args) {
        SpringApplication.run(Hw18Application.class,args);

    }

}
