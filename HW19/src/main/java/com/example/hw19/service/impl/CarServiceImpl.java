package com.example.hw19.service.impl;

import com.example.hw19.exeption.CarNotFoundException;
import com.example.hw19.model.Car;
import com.example.hw19.repository.CarDao;
import com.example.hw19.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarDao carDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(int ownerId,Car car) {
        carDao.add(ownerId,car);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(int ownerId,int carId) throws CarNotFoundException {
        carDao.delete(ownerId,carId);
    }

    @Override
    public void update(Car car) {
        carDao.update(car);
    }

    @Override
    public Car getById(int id) throws CarNotFoundException {
        return carDao.getById(id);
    }

    @Override
    public List<Car> getAllByOwnerId(int id) {
        return carDao.getAllByOwnerId(id);
    }
}
