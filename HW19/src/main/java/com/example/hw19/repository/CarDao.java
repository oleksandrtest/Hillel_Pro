package com.example.hw19.repository;

import com.example.hw19.exeption.CarNotFoundException;
import com.example.hw19.mapper.CarRowMapper;
import com.example.hw19.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(int ownerId,Car car) {
        jdbcTemplate.update(
                "INSERT INTO garage.car(brand, model, color, type) VALUES(?,?,?,?)",
                car.getBrand(),car.getModel(),car.getColor(),car.getType());
    }

    public void delete(int ownerId,int carId) throws CarNotFoundException {
        try {
            jdbcTemplate.update(
                    "DELETE FROM garage.car USING garage.owner WHERE car_id =? AND car.owner_id=?",carId,ownerId);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new CarNotFoundException(carId);
        }
    }

    public void update(Car car) {
        jdbcTemplate.update(
                "UPDATE garage.car SET brand=?, model=?, color=?, type=?  WHERE car_id=?",
                car.getBrand(),car.getModel(),car.getColor(),car.getType(),car.getId());
    }

    public Car getById(int id) throws CarNotFoundException {
       Car car = jdbcTemplate.queryForObject("SELECT * FROM garage.car WHERE owner_id=?",
                new CarRowMapper());
        if (car != null) {
            return car;
        }
        throw new CarNotFoundException(id);
    }

    public List<Car> getAllByOwnerId(int id) {
        return jdbcTemplate.query(
                "SELECT * from garage.car WHERE owner_id=?", new CarRowMapper(), id);
    }
}