package com.example.exeption;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String name) {
        super("User with login " + name + " not found!");}
}
