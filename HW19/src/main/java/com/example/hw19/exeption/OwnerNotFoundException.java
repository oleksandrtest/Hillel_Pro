package com.example.hw19.exeption;

public class OwnerNotFoundException extends Exception {
    public OwnerNotFoundException(long id) {
        super("Owner with id " + id + " not found!");}
}
