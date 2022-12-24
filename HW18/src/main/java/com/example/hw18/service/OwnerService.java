package com.example.hw18.service;

import com.example.hw18.exeption.OwnerExistException;
import com.example.hw18.exeption.OwnerNotFoundException;
import com.example.hw18.model.Car;
import com.example.hw18.model.Owner;

import java.util.List;

public interface OwnerService {

    void add(Owner owner) throws OwnerExistException;
    void deleteById(int id);
    Owner getById(int id) throws OwnerNotFoundException;
    List<Owner> getAll();
    void updateById(Owner owner) throws OwnerNotFoundException;
    void addCar(int ownerId, Car car) throws OwnerNotFoundException;
    void deleteCar(int ownerId, int carId) throws OwnerNotFoundException;
    List<Car> getAllCars(int id) throws OwnerNotFoundException;
}
