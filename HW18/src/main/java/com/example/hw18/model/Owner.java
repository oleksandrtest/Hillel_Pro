package com.example.hw18.model;

import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private int age;
    private List<Car> cars;
}
