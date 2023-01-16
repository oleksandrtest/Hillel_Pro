package com.example.hw19.mapper;

import com.example.hw19.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet rs,int rowNum) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt(1));
        car.setBrand(rs.getString(2));
        car.setModel(rs.getString(3));
        car.setColor(rs.getString(4));
        car.setType(rs.getString(5));
        car.setOwnerId(rs.getInt(6));
        return car;
    }
}
