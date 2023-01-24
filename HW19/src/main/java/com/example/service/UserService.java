package com.example.service;


import com.example.exeption.UserExistException;
import com.example.model.User;

public interface UserService {
    void saveUser(User user) throws UserExistException;
}

