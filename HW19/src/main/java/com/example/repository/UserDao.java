package com.example.repository;

import com.example.exeption.UserExistException;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int add(User user) throws UserExistException {
        return jdbcTemplate.update(
                "INSERT INTO garage.user(username, password, enable, role) " +
                        "VALUES(?,?,?,?)",
                user.getUsername(),user.getPassword(),user.getIsEnable(),user.getRole().toString());
    }
}
