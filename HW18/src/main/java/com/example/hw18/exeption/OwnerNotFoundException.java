package com.example.hw18.exeption;

public class OwnerNotFoundException extends Exception {
    public OwnerNotFoundException(long id) {
        super("Owner with id " + id + " not found!");}
}
