package com.example.hw19.repository;

import com.example.hw19.exeption.OwnerExistException;
import com.example.hw19.exeption.OwnerNotFoundException;
import com.example.hw19.mapper.OwnerRowMapper;
import com.example.hw19.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OwnerDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(Owner owner) throws OwnerExistException {
        jdbcTemplate.update(
                    "INSERT INTO garage.owner(first_name,last_name,gender,age) " +
                            "VALUES(?,?,?,?)",
                    owner.getFirstName(),owner.getLastName(),owner.getGender(),owner.getAge());
    }

    public void updateById(Owner owner) {
        jdbcTemplate.update(
                "UPDATE garage.owner SET first_name=?, last_name=?, gender=?, age=? WHERE owner_id=?",
                owner.getFirstName(),owner.getLastName(),owner.getGender(),owner.getAge(),owner.getId());
    }
    public void deleteById(int id)  {
            jdbcTemplate.update("DELETE FROM garage.owner WHERE owner_id=?",id);
    }

    public Owner getById(int id) throws OwnerNotFoundException {
       Owner owner = jdbcTemplate.queryForObject("SELECT * FROM garage.owner WHERE owner_id=?",
                    new OwnerRowMapper(), id);
        if (owner != null) {
            return owner;
        }
        throw new OwnerNotFoundException(id);
    }

    public List<Owner> getAll() {
        return jdbcTemplate.query(
                "SELECT * from garage.owner", new OwnerRowMapper());
    }

}