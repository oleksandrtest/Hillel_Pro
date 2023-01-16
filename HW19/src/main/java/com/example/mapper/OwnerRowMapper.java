package com.example.hw19.mapper;

import com.example.hw19.model.Owner;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerRowMapper implements RowMapper<Owner> {

    @Override
    public Owner mapRow(ResultSet rs,int rowNum) throws SQLException {
        Owner owner = new Owner();
        owner.setId(rs.getInt(1));
        owner.setFirstName(rs.getString(2));
        owner.setLastName(rs.getString(3));
        owner.setGender(rs.getString(4));
        owner.setAge(rs.getInt(5));
        return owner;
    }
}
