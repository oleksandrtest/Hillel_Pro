package com.example.hw19.service;

import com.example.hw19.exeption.CarNotFoundException;
import com.example.hw19.model.Car;

import java.util.List;

public interface CarService {

    void add(int ownerId,Car car);
    void delete(int ownerId, int carId) throws CarNotFoundException;
    void update(Car car);
    Car getById(int id) throws CarNotFoundException;
    List<Car> getAllByOwnerId(int id);

}
