package com.example.exeption;

public class UserExistException extends Exception {
    public UserExistException(long id) {
        super("User with id " + id + " exist");}
}
