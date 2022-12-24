package com.example.hw18.exeption;

public class OwnerExistException extends Exception {
    public OwnerExistException(long id) {
        super("Owner with id " + id + " exist, please update this Owner");}
}
