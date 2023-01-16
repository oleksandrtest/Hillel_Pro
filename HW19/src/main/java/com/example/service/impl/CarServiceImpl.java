package com.example.service.impl;

import com.example.exeption.CarNotFoundException;
import com.example.model.Car;
import com.example.repository.CarDao;
import com.example.service.CarService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    public CarServiceImpl(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(int ownerId,Car car) {
        carDao.add(ownerId,car);
    }

    @Override
    @CacheEvict(value = "cars", key = "#carId")
    @Transactional(rollbackFor = Exception.class)
    public void delete(int ownerId,int carId) throws CarNotFoundException {
        carDao.delete(ownerId,carId);
    }

    @Override
    public void update(Car car) {
        carDao.update(car);
    }

    @Override
    @Cacheable(value = "car", key = "#id")
    public Car getById(int id) throws CarNotFoundException {
        return carDao.getById(id);
    }

    @Override
    @Cacheable(value = "cars", key = "#id")
    public List<Car> getAllByOwnerId(int id) {
        return carDao.getAllByOwnerId(id);
    }
}
