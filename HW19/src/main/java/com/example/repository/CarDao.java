package com.example.repository;

import com.example.exeption.CarNotFoundException;
import com.example.mapper.CarRowMapper;
import com.example.model.Car;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CarDao {

    private JdbcTemplate jdbcTemplate;

    public CarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean add(int ownerId,Car car) {
        car.setOwnerId(ownerId);
        return jdbcTemplate.update(
                "INSERT INTO garage.car(brand, model, color, type,owner_id) VALUES(?,?,?,?,?)",
                car.getBrand(),car.getModel(),car.getColor(),car.getType(), car.getOwnerId()) == 1;
    }

    public boolean delete(int ownerId,int carId) throws CarNotFoundException {
        try {
            return jdbcTemplate.update(
                    "DELETE FROM garage.car USING garage.owner WHERE car_id =? AND car.owner_id=?",carId,ownerId) == 1;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new CarNotFoundException(carId);
        }
    }

    public boolean update(Car car) {
        return jdbcTemplate.update(
                "UPDATE garage.car SET brand=?, model=?, color=?, type=?  WHERE car_id=?",
                car.getBrand(),car.getModel(),car.getColor(),car.getType(),car.getId()) == 1;
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