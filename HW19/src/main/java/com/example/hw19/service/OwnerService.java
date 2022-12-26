package com.example.hw19.service;

import com.example.hw19.exeption.OwnerExistException;
import com.example.hw19.exeption.OwnerNotFoundException;
import com.example.hw19.model.Owner;

import java.util.List;

public interface OwnerService {

    void add(Owner owner) throws OwnerExistException;
    void updateById(Owner owner);
    void deleteById(int id) throws OwnerNotFoundException;
    Owner getById(int id) throws OwnerNotFoundException;
    List<Owner> getAll();
}
