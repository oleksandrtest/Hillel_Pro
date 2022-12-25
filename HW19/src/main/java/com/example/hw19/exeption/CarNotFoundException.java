package com.example.hw19.exeption;

public class CarNotFoundException extends Exception {
    public CarNotFoundException(long id) {
        super("Car with id " + id + " not found!");}
}
