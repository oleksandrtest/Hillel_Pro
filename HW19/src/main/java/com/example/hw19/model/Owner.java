package com.example.hw19.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
