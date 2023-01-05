package com.example.service.impl;

import com.example.exeption.CarNotFoundException;
import com.example.model.Car;
import com.example.repository.CarDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    public CarDao carDao;

    @Test
    void delete() throws CarNotFoundException {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        Car car = mock(Car.class);
        lenient().when(carDao.getById(car.getId())).thenReturn(car);
        carService.delete(car.getOwnerId(),car.getId());
        verify(carDao).delete(car.getOwnerId(),car.getId());
    }

    @Test
    void addAndUpdateTest() {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        Car car = mock(Car.class);
        doNothing().when(carDao).update(car);
        carService.update(car);
        verify(carDao, times(1)).update(car);
    }


    @Test
    void getById() throws CarNotFoundException {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        Car car = Car.builder().id(3).color("RED").brand("TOYOTA").model("RAV4").ownerId(1).build();
        when(carDao.getById(anyInt())).thenReturn(car);
        Car toyota = carService.getById(anyInt());
        Assertions.assertEquals(car, toyota);
    }

    @Test
    @DisplayName("Test assert exception")
    void getByIdThrowException() throws CarNotFoundException {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        when(carDao.getById(2)).thenThrow(new CarNotFoundException(2));
        Throwable throwable = assertThrows(CarNotFoundException.class, () -> {
            carService.getById(2);
        });

        Assertions.assertEquals(CarNotFoundException.class, throwable.getClass());
    }

    @Test
    void carNotFoundById() throws CarNotFoundException {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        Car car = mock(Car.class);
        when(carDao.getById(anyInt())).thenReturn(null);
        Car toyota = carService.getById(anyInt());
        Assertions.assertNotEquals(car ,toyota);
    }

    @Test
    void getAllByOwnerId() {
        CarServiceImpl carService = new CarServiceImpl(carDao);
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder().id(3).color("RED").brand("TOYOTA").model("RAV4").ownerId(1).build());
        cars.add(Car.builder().id(2).color("WHITE").brand("TOYOTA").model("CAMRY").ownerId(1).build());
        cars.add(Car.builder().id(1).color("RED").brand("MAZDA").model("CX3").ownerId(1).build());
        when(carService.getAllByOwnerId(anyInt())).thenReturn(cars);
        List<Car> ownerCars = carService.getAllByOwnerId(anyInt());
        Assertions.assertEquals(cars, ownerCars);
    }
}